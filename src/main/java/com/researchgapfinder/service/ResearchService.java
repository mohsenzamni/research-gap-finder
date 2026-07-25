package com.researchgapfinder.service;

import com.researchgapfinder.ai.LlmProvider;
import com.researchgapfinder.domain.*;
import com.researchgapfinder.literature.LiteratureSearchProvider;
import com.researchgapfinder.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ResearchService {
    private static final double NO_RESULTS_CONFIDENCE = 0.35;
    private static final double HAS_RESULTS_CONFIDENCE = 0.50;
    private static final double DISCOVERED_GAP_CONFIDENCE = 0.55;
    private static final double LIMITATION_EVIDENCE_RELEVANCE = 0.80;
    private final ProjectRepository projects;
    private final PaperRepository papers;
    private final PaperEvidenceRepository paperEvidence;
    private final GapRepository gaps;
    private final ValidationResultRepository validations;
    private final GapEvidenceRepository gapEvidence;
    private final IdeaRepository ideas;
    private final LlmProvider llm;
    private final LiteratureSearchProvider literature;

    public ResearchService(ProjectRepository projects, PaperRepository papers, PaperEvidenceRepository paperEvidence, GapRepository gaps,
                           ValidationResultRepository validations, GapEvidenceRepository gapEvidence, IdeaRepository ideas,
                           LlmProvider llm, LiteratureSearchProvider literature) {
        this.projects = projects;
        this.papers = papers;
        this.paperEvidence = paperEvidence;
        this.gaps = gaps;
        this.validations = validations;
        this.gapEvidence = gapEvidence;
        this.ideas = ideas;
        this.llm = llm;
        this.literature = literature;
    }

    @Transactional
    public ResearchProject createProject(ResearchProject project) { return projects.save(project); }
    public ResearchProject project(UUID id) { return projects.findById(id).orElseThrow(() -> missing("project", id)); }
    public List<ResearchProject> projects() { return projects.findAll(); }

    @Transactional
    public Paper addPaper(UUID projectId, PaperInput input) {
        ResearchProject project = project(projectId);
        EvidenceLevel level = input.fullText() != null && !input.fullText().isBlank()
                ? EvidenceLevel.FULL_TEXT : input.abstractText() != null && !input.abstractText().isBlank()
                ? EvidenceLevel.ABSTRACT : EvidenceLevel.METADATA;
        Paper paper = new Paper(project, input.title(), level);
        paper.setAuthors(input.authors());
        paper.setPublicationYear(input.publicationYear());
        paper.setDoi(input.doi());
        paper.setSourceUrl(input.sourceUrl());
        paper.setAbstractText(input.abstractText());
        paper.setFullText(input.fullText());
        Paper saved = papers.save(paper);
        paperEvidence.save(new PaperEvidence(saved, level,
                input.fullText() != null && !input.fullText().isBlank() ? SourceType.USER_UPLOAD : SourceType.USER_INPUT,
                input.sourceUrl()));
        return saved;
    }
    public List<Paper> papers(UUID projectId) { return papers.findByProjectId(projectId); }
    public LlmProvider.PaperAnalysis analysePaper(UUID paperId) {
        return llm.extract(papers.findById(paperId).orElseThrow(() -> missing("paper", paperId)));
    }

    @Transactional
    public ResearchGap addGap(UUID projectId, GapInput input) {
        return gaps.save(new ResearchGap(project(projectId), input.type(), input.title(), input.description(),
                clamp(input.confidence(), 0, 1)));
    }
    public List<ResearchGap> gaps(UUID projectId) { return gaps.findByProjectId(projectId); }

    @Transactional
    public List<ResearchGap> discoverGaps(UUID projectId) {
        ResearchProject project = project(projectId);
        List<ResearchGap> discovered = new ArrayList<>();
        for (Paper paper : papers.findByProjectId(projectId)) {
            LlmProvider.PaperAnalysis analysis = llm.extract(paper);
            for (String limitation : analysis.limitations()) {
                String title = "Unresolved limitation: " + shorten(limitation, 900);
                boolean exists = gaps.findByProjectId(projectId).stream()
                        .anyMatch(gap -> gap.getTitle().equals(title));
                if (exists) continue;
                ResearchGap gap = gaps.save(new ResearchGap(project, classifyGap(limitation),
                        title, limitation, DISCOVERED_GAP_CONFIDENCE));
                gapEvidence.save(new GapEvidence(gap, paper, EvidenceType.LIMITATION,
                        LIMITATION_EVIDENCE_RELEVANCE, limitation));
                discovered.add(gap);
            }
        }
        return discovered;
    }

    private GapType classifyGap(String limitation) {
        String lower = limitation.toLowerCase();
        if (lower.contains("variable") || lower.contains("measure")) return GapType.VARIABLE_GAP;
        if (lower.contains("population") || lower.contains("sample")) return GapType.POPULATION_GAP;
        if (lower.contains("geograph") || lower.contains("country") || lower.contains("region")) {
            return GapType.GEOGRAPHIC_GAP;
        }
        if (lower.contains("theor")) return GapType.THEORETICAL_GAP;
        if (lower.contains("time") || lower.contains("longitudinal")) return GapType.TEMPORAL_GAP;
        if (lower.contains("context") || lower.contains("setting")) return GapType.CONTEXT_GAP;
        return GapType.METHODOLOGICAL_GAP;
    }

    private String shorten(String value, int maxLength) {
        return value.length() <= maxLength ? value : value.substring(0, maxLength - 1) + "…";
    }

    public List<GapEvidence> evidence(UUID gapId) {
        if (!gaps.existsById(gapId)) throw missing("gap", gapId);
        return gapEvidence.findByGapId(gapId);
    }

    @Transactional
    public ValidationResult validateGap(UUID gapId) {
        ResearchGap gap = gaps.findById(gapId).orElseThrow(() -> missing("gap", gapId));
        String query = gap.getTitle() + " " + gap.getDescription();
        List<LiteratureSearchProvider.LiteratureRecord> results = literature.search(query);
        ValidationStatus status = results.isEmpty() ? ValidationStatus.PARTIALLY_VALIDATED : ValidationStatus.WEAK;
        String summary = results.isEmpty()
                ? "No external records were returned; the candidate gap remains uncertain and requires further search."
                : "External literature returned " + results.size() + " record(s); inspect them before treating this candidate as a gap.";
        ValidationResult result = validations.save(new ValidationResult(gap, status,
                results.isEmpty() ? NO_RESULTS_CONFIDENCE : HAS_RESULTS_CONFIDENCE,
                summary, List.of(query)));
        gap.setStatus(status == ValidationStatus.PARTIALLY_VALIDATED ? GapStatus.PARTIALLY_VALIDATED : GapStatus.WEAK);
        gaps.save(gap);
        return result;
    }
    public List<ValidationResult> validations(UUID gapId) { return validations.findByGapId(gapId); }

    @Transactional
    public ResearchIdea addIdea(UUID projectId, IdeaInput input) {
        ResearchGap gap = gaps.findById(input.gapId()).orElseThrow(() -> missing("gap", input.gapId()));
        if (!gap.getProject().getId().equals(projectId)) {
            throw new IllegalArgumentException("Gap " + input.gapId() + " belongs to project "
                    + gap.getProject().getId() + " but was accessed via project " + projectId);
        }
        return ideas.save(new ResearchIdea(project(projectId), gap, input.title(), input.researchQuestion(),
                input.rationale(), clamp(input.score(), 0, 100)));
    }
    public List<ResearchIdea> ideas(UUID projectId) { return ideas.findByProjectIdOrderByScoreDesc(projectId); }

    private RuntimeException missing(String type, UUID id) {
        return new NoSuchElementException(type + " not found: " + id);
    }
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    public record PaperInput(String title, String authors, Integer publicationYear, String doi, String sourceUrl,
                             String abstractText, String fullText) {}
    public record GapInput(GapType type, String title, String description, double confidence) {}
    public record IdeaInput(UUID gapId, String title, String researchQuestion, String rationale, double score) {}
}

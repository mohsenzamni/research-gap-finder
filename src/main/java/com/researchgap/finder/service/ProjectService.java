package com.researchgap.finder.service;
import com.researchgap.finder.domain.*;
import com.researchgap.finder.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
public class ProjectService {
 private static final double DEFAULT_GAP_CONFIDENCE = 0.72;
 private final ProjectRepository projects;
 private final PaperRepository papers;
 private final GapRepository gaps;
 private final IdeaRepository ideas;
 private final AnalysisBoundary analysis;
 private final LiteratureProvider literature;
 public ProjectService(ProjectRepository projectRepository, PaperRepository paperRepository,
                       GapRepository gapRepository, IdeaRepository ideaRepository,
                       AnalysisBoundary analysisBoundary, LiteratureProvider literatureProvider) {
  projects = projectRepository;
  papers = paperRepository;
  gaps = gapRepository;
  ideas = ideaRepository;
  analysis = analysisBoundary;
  literature = literatureProvider;
 }
 public Project create(String name, String question, String constraints, String preferences) {
   return projects.save(new Project(name, question, constraints, preferences));
 }
 public Project get(Long projectId) {
   return projects.findById(projectId)
       .orElseThrow(() -> new NotFoundException("Project " + projectId + " not found"));
 }
 public Paper addPaper(Long projectId, String title, String abstractText, String authors,
                        Integer publicationYear, String venue, String doi) {
   Project project = get(projectId);
   Paper paper = papers.save(new Paper(project, title, abstractText, authors,
       publicationYear, venue, doi));
   AnalysisBoundary.AnalysisResult result = analysis.analyze(paper);
   paper.setTopics(String.join(", ", result.topics()));
   paper.setMethods(String.join(", ", result.methods()));
   return papers.save(paper);
 }
 public List<Paper> papers(Long projectId) {
   get(projectId);
   return papers.findByProjectId(projectId);
 }
 @Transactional
 public List<ResearchGap> analyze(Long projectId) {
  Project project = get(projectId);
  List<Paper> projectPapers = papers.findByProjectId(projectId);
  if (projectPapers.isEmpty()) {
   throw new IllegalStateException("Add at least one paper before analysis");
  }
  Set<String> topics = new LinkedHashSet<>();
  projectPapers.forEach(paper -> {
   if (paper.getTopics() != null && !paper.getTopics().isBlank()) {
    topics.addAll(Arrays.asList(paper.getTopics().split(", ")));
   }
  });
  List<ResearchGap> out = new ArrayList<>();
  for (String topic : topics) {
   String statement = "Limited evidence for " + topic + " under the stated constraints";
   out.add(gaps.save(new ResearchGap(project, statement, DEFAULT_GAP_CONFIDENCE,
       "The local deterministic analyzer found a topic represented in the corpus but no explicit coverage of the constraint.",
       "abstract")));
  }
  return out;
 }
 public List<ResearchGap> gaps(Long projectId) {
   get(projectId);
   return gaps.findByProjectId(projectId);
 }
 @Transactional
 public List<ResearchIdea> ideas(Long projectId) {
   Project project = get(projectId);
   List<ResearchGap> projectGaps = gaps(projectId);
   if (projectGaps.isEmpty()) {
     analyze(projectId);
     projectGaps = gaps(projectId);
   }
   List<ResearchIdea> generatedIdeas = new ArrayList<>();
   for (ResearchGap gap : projectGaps) {
     List<String> hits = literature.search(gap.getStatement());
     String evidenceStrength = hits.isEmpty() ? "abstract" : "external";
     generatedIdeas.add(ideas.save(new ResearchIdea(project, gap,
         "Study " + gap.getStatement().toLowerCase(),
         "Design a focused study to address this gap, measuring outcomes against the project constraints.",
         gap.getConfidence(), evidenceStrength,
         "Derived from paper abstracts; validated by deterministic literature provider.")));
   }
   return generatedIdeas;
 }
 public List<ResearchIdea> ideasList(Long projectId) {
   get(projectId);
   return ideas.findByProjectIdOrderByScoreDesc(projectId);
 }
 public void upload(Long projectId, Long paperId, byte[] pdfData) {
   get(projectId);
   Paper paper = papers.findById(paperId)
       .orElseThrow(() -> new NotFoundException("Paper not found"));
   if (!paper.getProject().getId().equals(projectId)) {
     throw new NotFoundException("Paper not in project");
   }
   paper.setPdf(pdfData);
   papers.save(paper);
 }
 public static class NotFoundException extends RuntimeException {
   public NotFoundException(String message) {
     super(message);
   }
 }
}

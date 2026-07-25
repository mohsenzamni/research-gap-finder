package com.researchgapfinder.api;

import com.researchgapfinder.ai.LlmProvider;
import com.researchgapfinder.domain.*;
import com.researchgapfinder.service.ResearchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ResearchController {
    private final ResearchService service;
    public ResearchController(ResearchService service) { this.service = service; }

    @PostMapping("/projects")
    @ResponseStatus(HttpStatus.CREATED)
    public ResearchProject createProject(@Valid @RequestBody ProjectRequest request) {
        ResearchProject project = new ResearchProject(request.name());
        project.setResearchDomain(request.researchDomain());
        project.setResearchArea(request.researchArea());
        project.setIndustry(request.industry());
        project.setGeography(request.geography());
        project.setPreferredResearchType(request.preferredResearchType());
        project.setPreferredMethods(request.preferredMethods());
        project.setAvailableData(request.availableData());
        project.setResearchGoal(request.researchGoal());
        return service.createProject(project);
    }
    @GetMapping("/projects") public List<ResearchProject> projects() { return service.projects(); }
    @GetMapping("/projects/{id}") public ResearchProject project(@PathVariable UUID id) { return service.project(id); }

    @PostMapping("/projects/{projectId}/papers")
    @ResponseStatus(HttpStatus.CREATED)
    public Paper addPaper(@PathVariable UUID projectId, @Valid @RequestBody PaperRequest request) {
        return service.addPaper(projectId, new ResearchService.PaperInput(request.title(), request.authors(),
                request.publicationYear(), request.doi(), request.sourceUrl(), request.abstractText(), request.fullText()));
    }
    @GetMapping("/projects/{projectId}/papers") public List<Paper> papers(@PathVariable UUID projectId) { return service.papers(projectId); }
    @PostMapping("/papers/{paperId}/analyse") public LlmProvider.PaperAnalysis analyse(@PathVariable UUID paperId) { return service.analysePaper(paperId); }

    @PostMapping("/projects/{projectId}/gaps")
    @ResponseStatus(HttpStatus.CREATED)
    public ResearchGap addGap(@PathVariable UUID projectId, @Valid @RequestBody GapRequest request) {
        return service.addGap(projectId, new ResearchService.GapInput(request.type(), request.title(), request.description(), request.confidence()));
    }
    @GetMapping("/projects/{projectId}/gaps") public List<ResearchGap> gaps(@PathVariable UUID projectId) { return service.gaps(projectId); }
    @PostMapping("/gaps/{gapId}/validate") public ValidationResult validate(@PathVariable UUID gapId) { return service.validateGap(gapId); }
    @GetMapping("/gaps/{gapId}/validations") public List<ValidationResult> validations(@PathVariable UUID gapId) { return service.validations(gapId); }

    @PostMapping("/projects/{projectId}/ideas")
    @ResponseStatus(HttpStatus.CREATED)
    public ResearchIdea addIdea(@PathVariable UUID projectId, @Valid @RequestBody IdeaRequest request) {
        return service.addIdea(projectId, new ResearchService.IdeaInput(request.gapId(), request.title(),
                request.researchQuestion(), request.rationale(), request.score()));
    }
    @GetMapping("/projects/{projectId}/ideas") public List<ResearchIdea> ideas(@PathVariable UUID projectId) { return service.ideas(projectId); }

    public record ProjectRequest(@NotBlank String name, String researchDomain, String researchArea, String industry,
                                 String geography, String preferredResearchType, String preferredMethods,
                                 String availableData, String researchGoal) {}
    public record PaperRequest(@NotBlank String title, String authors, Integer publicationYear, String doi,
                               String sourceUrl, String abstractText, String fullText) {}
    public record GapRequest(GapType type, @NotBlank String title, @NotBlank String description, double confidence) {}
    public record IdeaRequest(UUID gapId, @NotBlank String title, @NotBlank String researchQuestion,
                              String rationale, double score) {}
}

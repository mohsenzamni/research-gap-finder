package com.researchgap.finder.api;
import com.researchgap.finder.domain.*;
import com.researchgap.finder.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;
import static com.researchgap.finder.api.ApiModels.*;

@RestController @RequestMapping("/api/projects")
public class ProjectController {
 private final ProjectService service;
 public ProjectController(ProjectService projectService) {
  service = projectService;
 }
 @PostMapping
 public ResponseEntity<Project> create(@Valid @RequestBody CreateProject request) {
  Project project = service.create(request.name(), request.question(), request.constraints(),
      request.preferences());
  return ResponseEntity.status(HttpStatus.CREATED).body(project);
 }
 @GetMapping("/{projectId}")
 public Project get(@PathVariable Long projectId) {
  return service.get(projectId);
 }
 @PostMapping("/{projectId}/papers")
 public ResponseEntity<Paper> paper(@PathVariable Long projectId,
                                    @Valid @RequestBody AddPaper request) {
  Paper paper = service.addPaper(projectId, request.title(), request.abstractText(),
      request.authors(), request.publicationYear(), request.venue(), request.doi());
  return ResponseEntity.status(HttpStatus.CREATED).body(paper);
 }
 @GetMapping("/{projectId}/papers")
 public List<Paper> papers(@PathVariable Long projectId) {
  return service.papers(projectId);
 }
 @PostMapping(value = "/{projectId}/papers/{paperId}/pdf",
              consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
 public void pdf(@PathVariable Long projectId, @PathVariable Long paperId,
                 @RequestPart MultipartFile file) throws IOException {
  service.upload(projectId, paperId, file.getBytes());
 }
 @PostMapping("/{projectId}/analyze")
 public List<ResearchGap> analyze(@PathVariable Long projectId) {
  return service.analyze(projectId);
 }
 @GetMapping("/{projectId}/gaps")
 public List<ResearchGap> gaps(@PathVariable Long projectId) {
  return service.gaps(projectId);
 }
 @PostMapping("/{projectId}/ideas")
 public List<ResearchIdea> ideas(@PathVariable Long projectId) {
  return service.ideas(projectId);
 }
 @GetMapping("/{projectId}/ideas")
 public List<ResearchIdea> ideasList(@PathVariable Long projectId) {
  return service.ideasList(projectId);
 }
}

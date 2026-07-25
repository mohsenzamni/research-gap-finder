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
 public ProjectController(ProjectService s){service=s;}
 @PostMapping public ResponseEntity<Project> create(@Valid @RequestBody CreateProject r){return ResponseEntity.status(HttpStatus.CREATED).body(service.create(r.name(),r.question(),r.constraints(),r.preferences()));}
 @GetMapping("/{id}") public Project get(@PathVariable Long id){return service.get(id);}
 @PostMapping("/{id}/papers") public ResponseEntity<Paper> paper(@PathVariable Long id,@Valid @RequestBody AddPaper r){return ResponseEntity.status(HttpStatus.CREATED).body(service.addPaper(id,r.title(),r.abstractText(),r.authors(),r.publicationYear(),r.venue(),r.doi()));}
 @GetMapping("/{id}/papers") public List<Paper> papers(@PathVariable Long id){return service.papers(id);}
 @PostMapping("/{id}/papers/{paperId}/pdf",consumes=MediaType.MULTIPART_FORM_DATA_VALUE) public void pdf(@PathVariable Long id,@PathVariable Long paperId,@RequestPart MultipartFile file)throws IOException{service.upload(id,paperId,file.getBytes());}
 @PostMapping("/{id}/analyze") public List<ResearchGap> analyze(@PathVariable Long id){return service.analyze(id);}
 @GetMapping("/{id}/gaps") public List<ResearchGap> gaps(@PathVariable Long id){return service.gaps(id);}
 @PostMapping("/{id}/ideas") public List<ResearchIdea> ideas(@PathVariable Long id){return service.ideas(id);}
 @GetMapping("/{id}/ideas") public List<ResearchIdea> ideasList(@PathVariable Long id){return service.ideasList(id);}
}

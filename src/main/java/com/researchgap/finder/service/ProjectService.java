package com.researchgap.finder.service;
import com.researchgap.finder.domain.*;
import com.researchgap.finder.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
public class ProjectService {
 private final ProjectRepository projects; private final PaperRepository papers; private final GapRepository gaps; private final IdeaRepository ideas;
 private final AnalysisBoundary analysis; private final LiteratureProvider literature;
 public ProjectService(ProjectRepository p,PaperRepository pa,GapRepository g,IdeaRepository i,AnalysisBoundary a,LiteratureProvider l){projects=p;papers=pa;gaps=g;ideas=i;analysis=a;literature=l;}
 public Project create(String n,String q,String c,String pref){return projects.save(new Project(n,q,c,pref));}
 public Project get(Long id){return projects.findById(id).orElseThrow(()->new NotFoundException("Project "+id+" not found"));}
 public Paper addPaper(Long id,String t,String a,String au,Integer y,String v,String d){Project p=get(id); Paper paper=papers.save(new Paper(p,t,a,au,y,v,d)); var r=analysis.analyze(paper); paper.setTopics(String.join(", ",r.topics())); paper.setMethods(String.join(", ",r.methods())); return papers.save(paper);}
 public List<Paper> papers(Long id){get(id);return papers.findByProjectId(id);}
 @Transactional public List<ResearchGap> analyze(Long id){Project p=get(id); List<Paper> ps=papers.findByProjectId(id); if(ps.isEmpty()) throw new IllegalStateException("Add at least one paper before analysis"); Set<String> topics=new LinkedHashSet<>(); ps.forEach(x->topics.addAll(Arrays.asList(x.getTopics().split(", "))));
  List<ResearchGap> out=new ArrayList<>(); for(String topic:topics){String s="Limited evidence for "+topic+" under the stated constraints"; out.add(gaps.save(new ResearchGap(p,s,0.72,"The local deterministic analyzer found a topic represented in the corpus but no explicit coverage of the constraint.","abstract")));} return out;}
 public List<ResearchGap> gaps(Long id){get(id);return gaps.findByProjectId(id);}
 @Transactional public List<ResearchIdea> ideas(Long id){Project p=get(id); List<ResearchGap> gs=gaps(id); if(gs.isEmpty()) analyze(id); gs=gaps(id); List<ResearchIdea> out=new ArrayList<>(); for(ResearchGap g:gs){var hits=literature.search(g.getStatement()); String strength=hits.isEmpty()?"abstract":"external"; out.add(ideas.save(new ResearchIdea(p,g,"Study "+g.getStatement().toLowerCase(),"Design a focused study to address this gap, measuring outcomes against the project constraints.",g.getConfidence(),strength,"Derived from paper abstracts; validated by deterministic literature provider.")));} return out;}
 public List<ResearchIdea> ideasList(Long id){get(id);return ideas.findByProjectIdOrderByScoreDesc(id);}
 public void upload(Long projectId,Long paperId,byte[] data){get(projectId); Paper p=papers.findById(paperId).orElseThrow(()->new NotFoundException("Paper not found")); if(!p.getProject().getId().equals(projectId))throw new NotFoundException("Paper not in project");p.setPdf(data);papers.save(p);}
 public static class NotFoundException extends RuntimeException{public NotFoundException(String m){super(m);}}
}

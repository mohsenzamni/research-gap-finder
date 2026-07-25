package com.researchgap.finder.repo;
import com.researchgap.finder.domain.ResearchIdea;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface IdeaRepository extends JpaRepository<ResearchIdea,Long>{List<ResearchIdea> findByProjectIdOrderByScoreDesc(Long id);}

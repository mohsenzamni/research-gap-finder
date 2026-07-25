package com.researchgapfinder.repository;

import com.researchgapfinder.domain.ResearchIdea;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface IdeaRepository extends JpaRepository<ResearchIdea, UUID> {
    List<ResearchIdea> findByProjectIdOrderByScoreDesc(UUID projectId);
}

package com.researchgapfinder.repository;

import com.researchgapfinder.domain.ResearchGap;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface GapRepository extends JpaRepository<ResearchGap, UUID> {
    List<ResearchGap> findByProjectId(UUID projectId);
}

package com.researchgapfinder.repository;

import com.researchgapfinder.domain.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface PaperRepository extends JpaRepository<Paper, UUID> {
    List<Paper> findByProjectId(UUID projectId);
}

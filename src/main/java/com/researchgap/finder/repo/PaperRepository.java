package com.researchgap.finder.repo;
import com.researchgap.finder.domain.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface PaperRepository extends JpaRepository<Paper, Long> {
  List<Paper> findByProjectId(Long projectId);
}

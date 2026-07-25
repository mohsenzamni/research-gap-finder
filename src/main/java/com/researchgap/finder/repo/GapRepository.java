package com.researchgap.finder.repo;
import com.researchgap.finder.domain.ResearchGap;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface GapRepository extends JpaRepository<ResearchGap,Long>{List<ResearchGap> findByProjectId(Long id);}

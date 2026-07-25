package com.researchgapfinder.repository;

import com.researchgapfinder.domain.ValidationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ValidationResultRepository extends JpaRepository<ValidationResult, UUID> {
    List<ValidationResult> findByGapId(UUID gapId);
}

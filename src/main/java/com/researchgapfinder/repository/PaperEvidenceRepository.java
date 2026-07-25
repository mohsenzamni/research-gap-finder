package com.researchgapfinder.repository;

import com.researchgapfinder.domain.PaperEvidence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PaperEvidenceRepository extends JpaRepository<PaperEvidence, UUID> {}

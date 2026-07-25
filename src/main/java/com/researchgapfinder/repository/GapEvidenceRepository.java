package com.researchgapfinder.repository;

import com.researchgapfinder.domain.GapEvidence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GapEvidenceRepository extends JpaRepository<GapEvidence, UUID> {
    List<GapEvidence> findByGapId(UUID gapId);
}

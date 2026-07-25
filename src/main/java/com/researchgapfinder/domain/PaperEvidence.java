package com.researchgapfinder.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class PaperEvidence {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) private Paper paper;
    @Enumerated(EnumType.STRING) private EvidenceLevel evidenceLevel;
    @Enumerated(EnumType.STRING) private SourceType sourceType;
    private String sourceReference;
    private Instant extractedAt = Instant.now();
    protected PaperEvidence() {}
    public PaperEvidence(Paper paper, EvidenceLevel level, SourceType sourceType, String reference) {
        this.paper = paper;
        this.evidenceLevel = level;
        this.sourceType = sourceType;
        this.sourceReference = reference;
    }
    public UUID getId() { return id; }
    public Paper getPaper() { return paper; }
    public EvidenceLevel getEvidenceLevel() { return evidenceLevel; }
    public SourceType getSourceType() { return sourceType; }
    public String getSourceReference() { return sourceReference; }
}

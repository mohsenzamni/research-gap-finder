package com.researchgapfinder.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class GapEvidence {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) private ResearchGap gap;
    @ManyToOne(fetch = FetchType.LAZY) private Paper paper;
    @Enumerated(EnumType.STRING) private EvidenceType evidenceType;
    private Double relevance;
    @Column(length = 3000) private String supportingStatement;
    protected GapEvidence() {}
    public GapEvidence(ResearchGap gap, Paper paper, EvidenceType type, double relevance, String statement) {
        this.gap = gap; this.paper = paper; this.evidenceType = type; this.relevance = relevance; this.supportingStatement = statement;
    }
    public UUID getId() { return id; }
    public ResearchGap getGap() { return gap; }
    public Paper getPaper() { return paper; }
    public EvidenceType getEvidenceType() { return evidenceType; }
    public Double getRelevance() { return relevance; }
    public String getSupportingStatement() { return supportingStatement; }
}

package com.researchgapfinder.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
public class ResearchGap {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) private ResearchProject project;
    @Enumerated(EnumType.STRING) private GapType type;
    @Column(nullable = false, length = 1000) private String title;
    @Column(nullable = false, length = 5000) private String description;
    @Enumerated(EnumType.STRING) private GapStatus status = GapStatus.CANDIDATE;
    private Double confidence;
    private Instant createdAt = Instant.now();
    protected ResearchGap() {}
    public ResearchGap(ResearchProject project, GapType type, String title, String description, double confidence) {
        this.project = project; this.type = type; this.title = title; this.description = description; this.confidence = confidence;
    }
    public UUID getId() { return id; }
    public ResearchProject getProject() { return project; }
    public GapType getType() { return type; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public GapStatus getStatus() { return status; }
    public void setStatus(GapStatus v) { status = v; }
    public Double getConfidence() { return confidence; }
}

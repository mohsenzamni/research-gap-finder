package com.researchgapfinder.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
public class ValidationResult {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) private ResearchGap gap;
    @Enumerated(EnumType.STRING) private ValidationStatus status;
    private Double confidence;
    @Column(length = 5000) private String summary;
    @ElementCollection private List<String> searchedQueries = new ArrayList<>();
    private Instant validatedAt = Instant.now();
    protected ValidationResult() {}
    public ValidationResult(ResearchGap gap, ValidationStatus status, double confidence, String summary, List<String> queries) {
        this.gap = gap; this.status = status; this.confidence = confidence; this.summary = summary; this.searchedQueries.addAll(queries);
    }
    public UUID getId() { return id; }
    public ResearchGap getGap() { return gap; }
    public ValidationStatus getStatus() { return status; }
    public Double getConfidence() { return confidence; }
    public String getSummary() { return summary; }
    public List<String> getSearchedQueries() { return searchedQueries; }
}

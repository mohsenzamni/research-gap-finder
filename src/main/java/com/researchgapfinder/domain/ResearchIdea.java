package com.researchgapfinder.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class ResearchIdea {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) private ResearchProject project;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) private ResearchGap gap;
    @Column(nullable = false, length = 1000) private String title;
    @Column(length = 3000) private String researchQuestion;
    @Column(length = 5000) private String rationale;
    @Column(length = 3000) private String suggestedMethodology;
    @Column(length = 3000) private String dataRequirements;
    @Column(length = 3000) private String potentialContribution;
    @Column(length = 3000) private String mainRisks;
    private Double score;
    private Instant createdAt = Instant.now();
    protected ResearchIdea() {}
    public ResearchIdea(ResearchProject project, ResearchGap gap, String title, String question, String rationale, double score) {
        this.project = project;
        this.gap = gap;
        this.title = title;
        this.researchQuestion = question;
        this.rationale = rationale;
        this.score = score;
    }
    public UUID getId() { return id; }
    public ResearchProject getProject() { return project; }
    public ResearchGap getGap() { return gap; }
    public String getTitle() { return title; }
    public String getResearchQuestion() { return researchQuestion; }
    public String getRationale() { return rationale; }
    public Double getScore() { return score; }
}

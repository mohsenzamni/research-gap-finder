package com.researchgapfinder.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Paper {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) private ResearchProject project;
    @Column(nullable = false, length = 1000) private String title;
    @Column(length = 4000) private String authors;
    private Integer publicationYear;
    private String doi;
    private String sourceUrl;
    @Column(length = 10000) private String abstractText;
    @Lob private String fullText;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private EvidenceLevel evidenceLevel;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    protected Paper() {}
    public Paper(ResearchProject project, String title, EvidenceLevel level) {
        this.project = project; this.title = title; this.evidenceLevel = level;
    }
    @PreUpdate void touch() { updatedAt = Instant.now(); }
    public UUID getId() { return id; }
    public ResearchProject getProject() { return project; }
    public String getTitle() { return title; }
    public void setTitle(String v) { title = v; }
    public String getAuthors() { return authors; }
    public void setAuthors(String v) { authors = v; }
    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer v) { publicationYear = v; }
    public String getDoi() { return doi; }
    public void setDoi(String v) { doi = v; }
    public String getSourceUrl() { return sourceUrl; }
    public void setSourceUrl(String v) { sourceUrl = v; }
    public String getAbstractText() { return abstractText; }
    public void setAbstractText(String v) { abstractText = v; }
    public String getFullText() { return fullText; }
    public void setFullText(String v) { fullText = v; }
    public EvidenceLevel getEvidenceLevel() { return evidenceLevel; }
    public void setEvidenceLevel(EvidenceLevel v) { evidenceLevel = v; }
}

package com.researchgapfinder.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
public class ResearchProject {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false) private String name;
    private String researchDomain;
    private String researchArea;
    private String industry;
    private String geography;
    private String preferredResearchType;
    private String preferredMethods;
    @Column(length = 2000) private String availableData;
    private String researchGoal;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    protected ResearchProject() {}
    public ResearchProject(String name) { this.name = name; }
    @PreUpdate void touch() { updatedAt = Instant.now(); }
    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getResearchDomain() { return researchDomain; }
    public void setResearchDomain(String v) { researchDomain = v; }
    public String getResearchArea() { return researchArea; }
    public void setResearchArea(String v) { researchArea = v; }
    public String getIndustry() { return industry; }
    public void setIndustry(String v) { industry = v; }
    public String getGeography() { return geography; }
    public void setGeography(String v) { geography = v; }
    public String getPreferredResearchType() { return preferredResearchType; }
    public void setPreferredResearchType(String v) { preferredResearchType = v; }
    public String getPreferredMethods() { return preferredMethods; }
    public void setPreferredMethods(String v) { preferredMethods = v; }
    public String getAvailableData() { return availableData; }
    public void setAvailableData(String v) { availableData = v; }
    public String getResearchGoal() { return researchGoal; }
    public void setResearchGoal(String v) { researchGoal = v; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}

package com.mohsenzamni.researchgapfinder.project;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

@Entity
@Table(name = "research_project")
public class ResearchProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    private String researchDomain;
    private String researchArea;
    private String industry;
    private String geography;
    private String researchGoal;
    private Instant createdAt;
    private Instant updatedAt;

    protected ResearchProject() {
    }

    public ResearchProject(String name, String researchDomain, String researchArea, String industry,
                           String geography, String researchGoal) {
        this.name = name;
        this.researchDomain = researchDomain;
        this.researchArea = researchArea;
        this.industry = industry;
        this.geography = geography;
        this.researchGoal = researchGoal;
    }

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getResearchDomain() { return researchDomain; }
    public void setResearchDomain(String researchDomain) { this.researchDomain = researchDomain; }
    public String getResearchArea() { return researchArea; }
    public void setResearchArea(String researchArea) { this.researchArea = researchArea; }
    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }
    public String getGeography() { return geography; }
    public void setGeography(String geography) { this.geography = geography; }
    public String getResearchGoal() { return researchGoal; }
    public void setResearchGoal(String researchGoal) { this.researchGoal = researchGoal; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}

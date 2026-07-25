package com.researchgap.finder.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ResearchGap {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 @ManyToOne(optional = false)
 @JsonIgnore
 private Project project;
 @Column(length = 4000, nullable = false)
 private String statement;
 private double confidence;
 @Column(length = 4000)
 private String rationale;
 @Column(length = 4000)
 private String evidence;
 protected ResearchGap() {}
 public ResearchGap(Project project, String statement, double confidence,
                    String rationale, String evidence) {
   this.project = project;
   this.statement = statement;
   this.confidence = confidence;
   this.rationale = rationale;
   this.evidence = evidence;
 }
 public Long getId() { return id; }
 public Project getProject() { return project; }
 public String getStatement() { return statement; }
 public double getConfidence() { return confidence; }
 public String getRationale() { return rationale; }
 public String getEvidence() { return evidence; }
}

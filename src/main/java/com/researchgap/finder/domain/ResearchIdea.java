package com.researchgap.finder.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ResearchIdea {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) @JsonIgnore private Project project; @ManyToOne @JsonIgnore private ResearchGap gap;
 @Column(length=4000,nullable=false) private String title; @Column(length=8000) private String proposal;
 private double score; private String evidenceStrength; @Column(length=8000) private String provenance;
 protected ResearchIdea(){} public ResearchIdea(Project p,ResearchGap g,String t,String pr,double s,String es,String prov){project=p;gap=g;title=t;proposal=pr;score=s;evidenceStrength=es;provenance=prov;}
 public Long getId(){return id;} public String getTitle(){return title;} public String getProposal(){return proposal;} public double getScore(){return score;} public String getEvidenceStrength(){return evidenceStrength;} public String getProvenance(){return provenance;} public ResearchGap getGap(){return gap;}
}

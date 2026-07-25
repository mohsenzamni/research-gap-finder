package com.researchgap.finder.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Paper {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(optional = false)
  @JsonIgnore
  private Project project;
  @Column(nullable = false)
  private String title;
  @Column(length = 20000)
  private String abstractText;
  private String authors;
  private Integer publicationYear;
  private String venue;
  private String doi;
  @Lob
  @JsonIgnore
  private byte[] pdf;
  @Column(length = 10000)
  private String topics;
  @Column(length = 10000)
  private String methods;
  protected Paper(){}
  public Paper(Project project, String title, String abstractText, String authors,
               Integer publicationYear, String venue, String doi) {
    this.project = project;
    this.title = title;
    this.abstractText = abstractText;
    this.authors = authors;
    this.publicationYear = publicationYear;
    this.venue = venue;
    this.doi = doi;
  }
  public Long getId() { return id; }
  public Project getProject() { return project; }
  public String getTitle() { return title; }
  public String getAbstractText() { return abstractText; }
  public String getAuthors() { return authors; }
  public Integer getPublicationYear() { return publicationYear; }
  public String getVenue() { return venue; }
  public String getDoi() { return doi; }
  public byte[] getPdf() { return pdf; }
  public void setPdf(byte[] pdf) { this.pdf = pdf; }
  public String getTopics() { return topics; }
  public void setTopics(String topics) { this.topics = topics; }
  public String getMethods() { return methods; }
  public void setMethods(String methods) { this.methods = methods; }
}

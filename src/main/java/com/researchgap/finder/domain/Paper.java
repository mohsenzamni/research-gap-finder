package com.researchgap.finder.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Paper {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @ManyToOne(optional=false) @JsonIgnore private Project project;
  @Column(nullable=false) private String title;
  @Column(length=20000) private String abstractText;
  private String authors; private Integer publicationYear; private String venue; private String doi;
  @Lob @JsonIgnore private byte[] pdf;
  @Column(length=10000) private String topics;
  @Column(length=10000) private String methods;
  protected Paper(){}
  public Paper(Project p,String t,String a,String authors,Integer year,String venue,String doi){project=p;title=t;abstractText=a;this.authors=authors;publicationYear=year;this.venue=venue;this.doi=doi;}
  public Long getId(){return id;} public Project getProject(){return project;} public String getTitle(){return title;} public String getAbstractText(){return abstractText;}
  public String getAuthors(){return authors;} public Integer getPublicationYear(){return publicationYear;} public String getVenue(){return venue;} public String getDoi(){return doi;}
  public byte[] getPdf(){return pdf;} public void setPdf(byte[] p){pdf=p;} public String getTopics(){return topics;} public void setTopics(String v){topics=v;} public String getMethods(){return methods;} public void setMethods(String v){methods=v;}
}

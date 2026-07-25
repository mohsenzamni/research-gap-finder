package com.researchgap.finder.domain;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Project {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(nullable=false) private String name;
  @Column(length=4000) private String question;
  @Column(length=4000) private String constraints;
  @Column(length=4000) private String preferences;
  @OneToMany(mappedBy="project", cascade=CascadeType.ALL, orphanRemoval=true) private List<Paper> papers = new ArrayList<>();
  protected Project() {}
  public Project(String name,String question,String constraints,String preferences){this.name=name;this.question=question;this.constraints=constraints;this.preferences=preferences;}
  public Long getId(){return id;} public String getName(){return name;} public String getQuestion(){return question;}
  public String getConstraints(){return constraints;} public String getPreferences(){return preferences;} public List<Paper> getPapers(){return papers;}
}

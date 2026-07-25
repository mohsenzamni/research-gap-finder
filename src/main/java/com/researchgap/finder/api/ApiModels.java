package com.researchgap.finder.api;
import jakarta.validation.constraints.*;
public final class ApiModels {
 private ApiModels(){}
 public record CreateProject(@NotBlank String name,String question,String constraints,String preferences){}
 public record AddPaper(@NotBlank String title,String abstractText,String authors,Integer publicationYear,String venue,String doi){}
 public record GapView(Long id,String statement,double confidence,String rationale,String evidence){}
 public record IdeaView(Long id,String title,String proposal,double score,String evidenceStrength,String provenance){}
}

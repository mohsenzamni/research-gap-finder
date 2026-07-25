package com.researchgap.finder.service;
import com.researchgap.finder.domain.Paper;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.*;
@Component
public class DeterministicAnalysis implements AnalysisBoundary {
 public AnalysisResult analyze(Paper paper) {
  String text = (paper.getTitle() + " "
      + Optional.ofNullable(paper.getAbstractText()).orElse("")).toLowerCase();
  List<String> topics = Stream.of("machine learning", "health", "climate", "education",
      "social science", "optimization").filter(text::contains).toList();
  if (topics.isEmpty()) {
   topics = List.of("general research");
  }
  List<String> methods = Stream.of("survey", "experiment", "qualitative", "simulation",
      "review", "neural network").filter(text::contains).toList();
  if (methods.isEmpty()) {
   methods = List.of("unspecified");
  }
  return new AnalysisResult(topics, methods);
 }
}

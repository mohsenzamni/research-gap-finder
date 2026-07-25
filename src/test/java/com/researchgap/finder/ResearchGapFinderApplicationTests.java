package com.researchgap.finder;
import com.researchgap.finder.service.DeterministicAnalysis;
import com.researchgap.finder.domain.Paper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ResearchGapFinderApplicationTests {
 @Test
 void deterministicAnalysisExtractsSignals() {
  Paper paper = new Paper(null, "Climate survey",
      "A survey uses machine learning for climate research",
      null, null, null, null);
  var analysis = new DeterministicAnalysis().analyze(paper);
  assertTrue(analysis.topics().contains("climate"));
  assertTrue(analysis.methods().contains("survey"));
 }
}

package com.researchgap.finder;
import com.researchgap.finder.service.DeterministicAnalysis;
import com.researchgap.finder.domain.Paper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ResearchGapFinderApplicationTests {
 @Test void deterministicAnalysisExtractsSignals(){var p=new Paper(null,"Climate survey","A survey uses machine learning for climate research",null,null,null,null);var r=new DeterministicAnalysis().analyze(p);assertTrue(r.topics().contains("climate"));assertTrue(r.methods().contains("survey"));}
}

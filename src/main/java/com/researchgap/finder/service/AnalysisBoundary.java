package com.researchgap.finder.service;
import com.researchgap.finder.domain.Paper;
import java.util.*;
public interface AnalysisBoundary { AnalysisResult analyze(Paper paper); record AnalysisResult(List<String> topics,List<String> methods){} }

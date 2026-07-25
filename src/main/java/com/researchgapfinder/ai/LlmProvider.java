package com.researchgapfinder.ai;

import com.researchgapfinder.domain.Paper;

public interface LlmProvider {
    PaperAnalysis extract(Paper paper);
    record PaperAnalysis(String researchQuestion, String methodology, String[] themes, String[] limitations) {}
}

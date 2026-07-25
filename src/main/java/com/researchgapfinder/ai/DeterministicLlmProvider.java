package com.researchgapfinder.ai;

import com.researchgapfinder.domain.Paper;
import com.researchgapfinder.domain.EvidenceLevel;
import org.springframework.stereotype.Component;

@Component
public class DeterministicLlmProvider implements LlmProvider {
    @Override
    public PaperAnalysis extract(Paper paper) {
        String text = paper.getAbstractText() == null ? "" : paper.getAbstractText();
        String question = text.isBlank() ? null : "What relationships are examined in: " + paper.getTitle() + "?";
        String method = paper.getEvidenceLevel() == EvidenceLevel.METADATA
                ? null : "Not explicitly extracted by the MVP provider";
        return new PaperAnalysis(question, method, new String[0], new String[0]);
    }
}

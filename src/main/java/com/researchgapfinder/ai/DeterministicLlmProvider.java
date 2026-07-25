package com.researchgapfinder.ai;

import com.researchgapfinder.domain.Paper;
import com.researchgapfinder.domain.EvidenceLevel;
import org.springframework.stereotype.Component;

@Component
public class DeterministicLlmProvider implements LlmProvider {
    private static final String SENTENCE_BOUNDARY = "(?<=[.!?])\\s+";

    @Override
    public PaperAnalysis extract(Paper paper) {
        String text = paper.getFullText() != null && !paper.getFullText().isBlank()
                ? paper.getFullText() : paper.getAbstractText() == null ? "" : paper.getAbstractText();
        String question = text.isBlank() ? null : "What relationships are examined in: " + paper.getTitle() + "?";
        String method = paper.getEvidenceLevel() == EvidenceLevel.METADATA ? null : methodology(text);
        String[] limitations = text.isBlank() ? new String[0] : sentencesContaining(text,
                "limitation", "future research", "further research", "remain unclear");
        String[] themes = text.isBlank() ? new String[0] : themes(text);
        return new PaperAnalysis(question, method, themes, limitations);
    }

    private String methodology(String text) {
        String lower = text.toLowerCase();
        if (lower.contains("randomized") || lower.contains("experiment")) return "Experimental";
        if (lower.contains("survey") || lower.contains("questionnaire")) return "Survey";
        if (lower.contains("interview") || lower.contains("qualitative")) return "Qualitative";
        if (lower.contains("regression") || lower.contains("statistical analysis")) return "Quantitative";
        if (lower.contains("systematic review") || lower.contains("literature review")) return "Literature review";
        return null;
    }

    private String[] sentencesContaining(String text, String... markers) {
        return java.util.Arrays.stream(text.split(SENTENCE_BOUNDARY))
                .map(String::trim)
                .filter(sentence -> {
                    String lower = sentence.toLowerCase();
                    return java.util.Arrays.stream(markers).anyMatch(lower::contains);
                })
                .distinct()
                .limit(5)
                .toArray(String[]::new);
    }

    private String[] themes(String text) {
        return java.util.Arrays.stream(text.toLowerCase().split("[^a-z]+"))
                .filter(word -> word.length() >= 5)
                .distinct()
                .limit(5)
                .toArray(String[]::new);
    }
}

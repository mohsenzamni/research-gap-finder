package com.mohsenzamni.researchgapfinder.project;

public class ResearchProjectNotFoundException extends RuntimeException {

    public ResearchProjectNotFoundException(Long id) {
        super("Research project not found: " + id);
    }
}

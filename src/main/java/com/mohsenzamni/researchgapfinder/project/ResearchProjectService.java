package com.mohsenzamni.researchgapfinder.project;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResearchProjectService {

    private final ResearchProjectRepository repository;

    public ResearchProjectService(ResearchProjectRepository repository) {
        this.repository = repository;
    }

    public ResearchProject create(ResearchProject project) {
        return repository.save(project);
    }

    public List<ResearchProject> findAll() {
        return repository.findAll();
    }

    public ResearchProject findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResearchProjectNotFoundException(id));
    }
}

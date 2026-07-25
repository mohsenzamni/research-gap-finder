package com.mohsenzamni.researchgapfinder.project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResearchProjectServiceTest {

    @Mock
    private ResearchProjectRepository repository;

    @InjectMocks
    private ResearchProjectService service;

    @Test
    void createsProject() {
        ResearchProject project = new ResearchProject("Climate study", "Climate", "Adaptation",
                "Energy", "Europe", "Understand resilience");
        when(repository.save(project)).thenReturn(project);

        assertThat(service.create(project)).isSameAs(project);
        verify(repository).save(project);
    }

    @Test
    void listsProjects() {
        List<ResearchProject> projects = List.of(new ResearchProject("Study", null, null, null, null, null));
        when(repository.findAll()).thenReturn(projects);

        assertThat(service.findAll()).containsExactlyElementsOf(projects);
    }

    @Test
    void findsProjectById() {
        ResearchProject project = new ResearchProject("Study", null, null, null, null, null);
        when(repository.findById(42L)).thenReturn(Optional.of(project));

        assertThat(service.findById(42L)).isSameAs(project);
    }

    @Test
    void rejectsMissingProject() {
        when(repository.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(42L))
                .isInstanceOf(ResearchProjectNotFoundException.class)
                .hasMessage("Research project not found: 42");
    }
}

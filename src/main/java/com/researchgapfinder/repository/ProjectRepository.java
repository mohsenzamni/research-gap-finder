package com.researchgapfinder.repository;

import com.researchgapfinder.domain.ResearchProject;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<ResearchProject, UUID> {}

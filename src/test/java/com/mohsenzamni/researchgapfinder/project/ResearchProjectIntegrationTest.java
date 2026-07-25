package com.mohsenzamni.researchgapfinder.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class ResearchProjectIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("research_gap_finder")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ResearchProjectRepository repository;

    @Test
    void persistsProjectAgainstPostgres() {
        ResearchProject saved = repository.save(new ResearchProject("AI ethics", "Computer Science",
                "Responsible AI", "Technology", "Global", "Study governance"));

        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findById(saved.getId())).get()
                .extracting(ResearchProject::getName)
                .isEqualTo("AI ethics");
    }
}

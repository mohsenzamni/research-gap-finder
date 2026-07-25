# Research Gap Finder

An intentionally deterministic, local-first MVP for turning a small paper corpus into research-gap candidates and ranked ideas. It is a Java 21 / Spring Boot / Maven application using H2 and JPA.

## Run
```bash
mvn spring-boot:run
```
Swagger UI is available at `/swagger-ui.html`; the OpenAPI document is `/v3/api-docs`.

## API flow
1. `POST /api/projects` with `{ "name", "question", "constraints", "preferences" }`.
2. Add paper metadata and abstract with `POST /api/projects/{id}/papers`; optionally upload a PDF to `/papers/{paperId}/pdf`.
3. `POST /api/projects/{id}/analyze` extracts topics/methods, builds the landscape, and persists candidate gaps.
4. `POST /api/projects/{id}/ideas` validates each gap through the literature-provider boundary and generates ranked ideas.

`AnalysisBoundary` and `LiteratureProvider` are explicit seams for replacing deterministic implementations with hosted AI or scholarly APIs. Evidence provenance and strength are retained on each idea.

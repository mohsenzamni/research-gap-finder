# Research Gap Finder

An evidence-aware Spring Boot MVP for organizing papers, recording candidate gaps,
validating gaps against a literature-search boundary, and ranking research ideas.

## Run

```bash
mvn spring-boot:run
```

The API is rooted at `/api`. Create a project, add papers with title/abstract
or full text, create candidate gaps, validate them, and add ideas linked to a gap.
Use `POST /api/projects/{projectId}/gaps/discover` to turn explicit limitations
found in the stored papers into evidence-linked candidate gaps, then inspect them
with `GET /api/gaps/{gapId}/evidence`.
The MVP uses H2 by default and exposes an offline-safe literature provider; an
OpenAlex HTTP implementation can be supplied as a replacement bean.

Evidence levels are derived conservatively: `FULL_TEXT` requires full text,
`ABSTRACT` requires an abstract, otherwise the paper is `METADATA`. The analysis
boundary returns null for unavailable methodology rather than inventing details.

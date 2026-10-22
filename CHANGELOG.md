# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Initial project scaffolding and Maven build setup targeting Java 21 and Spring Boot 3.4.3.
- Official Apache Maven Wrapper 3.3.4 pinned to Maven 3.9.9.
- Core application entry point `IngestionApplication` rooted at package `io.tracelyt.ingestion`.
- Deterministic Spring Boot context smoke test `IngestionApplicationTest`.
- Operational Actuator endpoints baseline (`/actuator/health`, `/actuator/info`).
- Static code analysis with Maven Checkstyle plugin (`.checkstyle.xml`) and SpotBugs (`spotbugs-maven-plugin`).
- OWASP Dependency-Check plugin profile definition for vulnerability scanning.
- Multi-stage non-root container image definition (`Dockerfile`, `.dockerignore`).
- Local developer Compose configuration (`compose.yaml`) with documented database omission.
- GitHub Actions CI workflow (`ci.yml`) and Dependency Review workflow (`dependency-review.yml`).
- Renovate configuration (`renovate.json`) for automated dependency tracking.
- Open-source governance files (Apache 2.0 license, contributing guide, code of conduct, security policy, support guide, pull request and issue templates).
- Comprehensive documentation under `docs/` covering architecture, boundaries, development, operations, and threat model.

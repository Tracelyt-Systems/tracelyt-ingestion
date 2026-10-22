# Contributing to Tracelyt Ingestion

Thank you for your interest in contributing to Tracelyt Ingestion. We welcome bug reports, documentation improvements, architectural proposals, and code contributions.

## Current Project Phase

Tracelyt Ingestion is currently in its **foundational scaffolding stage**. Product capabilities (such as OTLP receivers, telemetry ingestion endpoints, Kafka streaming, and payload validation) are **intentionally not implemented yet**.

Contributions at this phase should focus on build configuration, developer tooling, documentation, and agreed roadmap milestones.

## Code of Conduct

All contributors and participants are expected to follow our [Code of Conduct](CODE_OF_CONDUCT.md).

## Getting Started

1. **Prerequisites**:
   - Java Development Kit (JDK) 21 LTS
   - Git
   - Docker (optional, for containerized testing)
2. **Fork and Clone**:
   ```bash
   git clone https://github.com/Tracelyt-Systems/tracelyt-ingestion.git
   cd tracelyt-ingestion
   ```
3. **Verify Build**:
   ```bash
   # Linux/macOS
   ./mvnw clean verify

   # Windows (PowerShell)
   .\mvnw.cmd clean verify
   ```

## Development Workflow

1. **Branching**:
   - Create focused branches off `main`:
     ```bash
     git checkout -b feature/issue-description
     # or
     git checkout -b fix/issue-description
     ```
2. **Coding Standards & Static Analysis**:
   - Code style is enforced by Checkstyle via [.checkstyle.xml](.checkstyle.xml).
   - Bytecode defect checks are enforced by SpotBugs.
   - Run checks locally before submitting:
     ```bash
     ./mvnw checkstyle:check
     ./mvnw spotbugs:check
     ```
3. **Tests**:
   - Every change must include appropriate unit tests.
   - Run test suite locally:
     ```bash
     ./mvnw test
     ```
4. **Commits**:
   - Write clear, concise, descriptive commit messages.

## Submitting Pull Requests

1. Push your branch to your GitHub fork.
2. Open a Pull Request against `main` of `Tracelyt-Systems/tracelyt-ingestion`.
3. Complete the [Pull Request Template](.github/PULL_REQUEST_TEMPLATE.md).
4. Verify all CI checks (compilation, Checkstyle, SpotBugs, and tests) pass.
5. Address reviewer feedback constructively.

## Security Disclosures

Please do **not** report security vulnerabilities through public GitHub issues or pull requests. Follow the instructions in [SECURITY.md](SECURITY.md).

# Code Style & Quality Standards

This document outlines the coding standards, naming conventions, and static-analysis expectations for Tracelyt Ingestion.

## Core Standards

- **Language Level**: Java 21 LTS (`.java-version`).
- **File Encoding**: UTF-8 without BOM across all source and resource files.
- **Line Endings**: LF (`\n`) for all source files, shell scripts, and documentation; CRLF (`\r\n`) reserved strictly for Windows batch files (`mvnw.cmd`).
- **Indentation**:
  - Java and XML: 4 spaces (no tabs).
  - YAML, Markdown, and JSON: 2 spaces.

## Checkstyle Rules

Code formatting rules are defined in [.checkstyle.xml](../../.checkstyle.xml):

1. **Line Length**: Maximum 120 characters per line (excluding import statements and long URLs).
2. **Imports**:
   - No wildcard imports (`AvoidStarImport`). Explicitly import each class.
   - Clean redundant and unused imports before submitting pull requests.
3. **Naming Conventions**:
   - Classes and Interfaces: `UpperCamelCase`
   - Methods and Variables: `lowerCamelCase`
   - Constants: `UPPER_SNAKE_CASE`
   - Packages: lowercase words rooted at `io.tracelyt.ingestion`
4. **Braces**: K&R style braces (`LeftCurly` on the same line, `RightCurly` on its own line) required on all control structures (`if`, `else`, `for`, `while`).

To verify style compliance:
```bash
./mvnw checkstyle:check
```

## SpotBugs Static Analysis

SpotBugs inspects compiled bytecode for potential bugs, concurrency defects, and resource leaks:
- Configured with `effort=Max` and `threshold=Medium`.
- Fails the build when defects are detected.

To run SpotBugs analysis:
```bash
./mvnw spotbugs:check
```

### Handling SpotBugs Findings
1. Treat every finding as a genuine code smell or defect first. Refactor code to eliminate the ambiguity.
2. In the rare case of a verified false positive, document the rationale in code comments or use `@SuppressFBWarnings` with an explicit reason.

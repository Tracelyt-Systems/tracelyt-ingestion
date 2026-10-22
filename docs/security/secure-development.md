# Secure Development Practices

This document outlines security hygiene, dependency management, and code review standards enforced across the Tracelyt Ingestion lifecycle.

## 1. Secret Hygiene & Leak Prevention

- **Never Commit Secrets**: Passwords, API keys, private certificates, and development tokens must never be committed to Git.
- **Git Ignore Safeguards**: The repository [.gitignore](../../.gitignore) strictly ignores `.env`, `.env.*`, certificates (`*.crt`, `*.pem`), private keys (`*.key`), and Java keystores (`*.jks`, `*.p12`).
- **Secret Committal Incident Procedure**:
  1. Immediately **revoke and rotate** the exposed credential in the target system.
  2. Treat the credential as fully compromised from the moment of push.
  3. Notify repository maintainers to coordinate history rewriting using tools like `git-filter-repo` if necessary.

## 2. Dependency Management & Vulnerability Scanning

- **Minimal Dependency Principle**: Add third-party libraries only when strictly necessary. Prefer standard Java 21 and Spring Boot managed libraries.
- **Static Analysis (SpotBugs)**: All pull requests must pass bytecode defect checks via `spotbugs-maven-plugin`.
- **Dependency Review in CI**: The [dependency-review.yml](../../.github/workflows/dependency-review.yml) workflow evaluates PR dependencies against known vulnerability databases and license restrictions.
- **On-Demand Vulnerability Audits**: The OWASP Dependency-Check plugin can be executed locally or in CI pipelines:
  ```bash
  ./mvnw dependency-check:check -Psecurity-scan
  ```

## 3. GitHub Actions Hardening

- **Pinned Commit SHAs**: All GitHub Actions are pinned to full immutable 40-character commit hashes with human-readable version comments (e.g. `actions/checkout@11bd71901bbe5b1630ceea73d27597364c9af683 # v4.2.2`).
- **Least-Privilege Token Permissions**: Workflows declare minimal permissions (`permissions: contents: read`). No workflow defaults to `write-all`.
- **No Untrusted Trigger Execution**: We avoid `pull_request_target` triggers to prevent unauthorized workflow execution on untrusted pull request code.
- **No Secret Printing**: Build scripts must never echo environment variables, authorization headers, or credentials into CI logs.

## 4. Code Review Standards

Every pull request must be reviewed by at least one maintainer with specific attention to:
- Input parsing and size boundaries on data structures.
- Concurrency safety and thread-safe data structures.
- Proper resource closure (`try-with-resources`).
- Absence of hardcoded test secrets or URLs.

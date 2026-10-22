# Architecture Decision Records (ADRs)

This directory contains records of foundational architectural decisions for Tracelyt Ingestion.

## What is an Architecture Decision Record?

An Architecture Decision Record (ADR) documents a significant architectural choice, capturing context, considered alternatives, positive consequences, and trade-offs.

## When to Propose an ADR

Contributors should open an ADR proposal via a pull request when a decision:
- Introduces or removes a foundational technology or dependency (e.g., protocol libraries, streaming clients).
- Modifies trust boundaries or ingestion security paradigms.
- Changes the operational relationship between Ingestion and downstream pipelines.

## ADR Template

New records should follow the naming format `NNNN-short-title.md` using the template below:

```markdown
# ADR-NNNN: Title of Decision

- **Status**: [ Proposed | Accepted | Superseded | Deprecated ]
- **Date**: YYYY-MM-DD
- **Author(s)**: @github-username

## Context
What is the problem or architectural requirement being addressed?

## Decision
What is the chosen approach, technology, or pattern?

## Considered Alternatives
1. Alternative A - Reasons for rejection
2. Alternative B - Reasons for rejection

## Consequences
- **Positive**: Benefits gained.
- **Negative / Trade-offs**: Costs or complexities introduced.
```

## Record Index

| ADR | Title | Status | Date |
| :--- | :--- | :--- | :--- |
| **[ADR-0001](0001-baseline-technology-stack.md)** | Baseline Technology Stack (Java 21, Spring Boot 3, Maven, Stateless Gateway) | Accepted | 2026-10-22 |

# ADR-0001: Baseline Technology Stack for Ingestion Gateway

- **Status**: Accepted
- **Date**: 2026-10-22
- **Author(s)**: @Tracelyt-Systems/maintainers

## Context
Tracelyt requires a dedicated, stateless ingestion service to receive telemetry emitted by application and infrastructure agents. The initial setup requires a reliable, typed, maintainable enterprise baseline with strict quality tooling and containerization, without introducing premature messaging or database dependencies before protocol boundaries are defined.

## Decision
We establish the following foundational technology choices:
1. **Language**: Java 21 LTS for high performance and modern language capabilities.
2. **Build System**: Apache Maven 3.9.9 via official Maven Wrapper 3.3.4.
3. **Application Framework**: Spring Boot 3.4.3 as the dependency and runtime manager.
4. **Stateless Gateway Paradigm**: The service will operate completely statelessly without a local relational database (PostgreSQL is omitted).
5. **Quality & Security Tooling**:
   - Maven Checkstyle Plugin 3.6.0 with Checkstyle 10.21.4.
   - SpotBugs Maven Plugin 4.10.3.0 for static bytecode analysis.
   - OWASP Dependency-Check configured via build profile for CVE scanning.
6. **Operational Probes**: Spring Boot Actuator for standard container health, liveness, and readiness diagnostics.
7. **Containerization**: Multi-stage Dockerfile running as a non-root user (`tracelyt` UID 10001) on `eclipse-temurin:21-jre-jammy`.

## Considered Alternatives
1. **Bundling PostgreSQL / Flyway**: Rejected. Unlike the Tracelyt Control Plane, the Ingestion service has no relational schema or metadata persistence responsibilities. Adding an unused database would introduce operational noise and false architectural expectations.
2. **Custom Telemetry Formats**: Rejected. Tracelyt will natively align with OpenTelemetry Protocol (OTLP) specifications when receiver implementation begins.
3. **Premature Kafka / Netty Integration**: Rejected. Downstream streaming brokers will be integrated in subsequent milestones once receiver contracts and serialization benchmarks are finalized.

## Consequences
- **Positive**:
  - Lean, focused footprint with zero unnecessary external dependencies.
  - Strong compile-time and static analysis guarantees.
  - Transparent developer onboarding and reproducible CI checks.
- **Negative / Trade-offs**:
  - The service currently exposes only operational management endpoints; ingestion receivers must be added in subsequent implementation milestones.

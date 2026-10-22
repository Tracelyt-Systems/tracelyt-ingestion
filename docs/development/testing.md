# Testing Strategy & Guidelines

This document details the testing architecture, conventions, and test execution procedures for Tracelyt Ingestion.

## Current Testing Foundation

The current repository contains an application context smoke test:
- **Location**: `src/test/java/io/tracelyt/ingestion/IngestionApplicationTest.java`
- **Purpose**: Verifies that the Spring Boot dependency graph, component scanning, and Actuator configuration bootstrap cleanly without errors.
- **Execution**: Configured with `@ActiveProfiles("test")` using `src/test/resources/application-test.yml`. Runs in-process without requiring external database or messaging infrastructure.

## Planned Test Categories

As ingestion features are introduced in future milestones, tests will follow this structure:

1. **Unit Tests (`*Test.java`)**:
   - For protocol decoders, payload size calculators, token extraction filters, and rate limiting algorithms.
   - Must run in-memory within milliseconds with zero external network or container dependencies.
2. **Protocol & Contract Tests (`*ProtocolTest.java`)**:
   - For verifying compliance with standard OpenTelemetry Protocol (OTLP/gRPC and OTLP/HTTP) schemas.
   - Use simulated OTLP client requests against mock endpoints.
3. **Integration & Backpressure Tests (`*IntegrationTest.java`)**:
   - For verifying batch buffering and forwarding to downstream message brokers (e.g., Kafka) using Testcontainers.

## Running Tests

### Run Full Test Suite
```bash
# Linux / macOS
./mvnw test

# Windows (PowerShell)
.\mvnw.cmd test
```

### Run a Specific Test
```bash
./mvnw test -Dtest=IngestionApplicationTest
```

### Memory Considerations
On memory-constrained developer workstations, JVM memory options can be supplied:
```powershell
$env:MAVEN_OPTS = "-Xmx512m -XX:MaxMetaspaceSize=256m"
.\mvnw.cmd test
```

## Testing Best Practices

- **Zero Flakiness**: Never use arbitrary `Thread.sleep()` in tests. Use explicit synchronization or Awaitility when testing asynchronous handoffs.
- **Fluent Assertions**: Use AssertJ (`assertThat(...)`) for clear, readable assertion messages.
- **Independent State**: Each test method must execute independently without depending on execution order.

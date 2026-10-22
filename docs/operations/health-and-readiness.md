# Health & Readiness Diagnostics

This document outlines the operational health probes provided by Tracelyt Ingestion and clarifies probe semantics across architecture phases.

## Available Endpoints (Current Foundation)

Management endpoints are exposed beneath `/actuator`:

| Path | Probe Type | Current Behavior |
| :--- | :--- | :--- |
| `/actuator/health` | Aggregate Health | Returns `{"status":"UP"}` when Spring context is alive. |
| `/actuator/health/liveness` | Kubernetes Liveness | Indicates the Java process is alive and not deadlocked. |
| `/actuator/health/readiness` | Kubernetes Readiness | Indicates the Spring Boot application has completed startup. |
| `/actuator/info` | Application Info | Returns service identification metadata. |

---

## Critical Distinction: Process Readiness vs. Ingestion Readiness

> [!WARNING]
> **Operational Warning**: The `/actuator/health` endpoint returning `UP` at this stage indicates **only that the Spring Boot Java process is running**.  
> It does **not** signify that Tracelyt Ingestion is ready to receive telemetry traffic. No OTLP listeners or telemetry consumers are implemented yet.

### Probe Semantics Evolution

1. **Current Foundation Stage**:
   - `liveness`: Process is running.
   - `readiness`: Internal context initialized.
2. **Future Ingestion Stage (Planned)**:
   - `liveness`: JVM threads healthy, memory pressure within thresholds.
   - `readiness`: OTLP network listeners bound, downstream message broker connected, backpressure queues not saturated. If downstream streaming brokers are unavailable, readiness will flip to `DOWN` to redirect traffic at the load balancer.

# Configuration Reference

Tracelyt Ingestion employs externalized configuration through Spring Boot, enabling identical binaries to run across environments with configuration injected via environment variables.

## Active Configuration Properties

The following properties are active in the current foundation:

| Environment Variable | Property Key | Purpose | Required? | Default Value |
| :--- | :--- | :--- | :--- | :--- |
| `PORT` | `server.port` | HTTP server listening port | Optional | `8081` |
| `SPRING_PROFILES_ACTIVE` | `spring.profiles.active` | Active configuration profile (`default`, `test`) | Optional | `default` |
| `MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE` | `management.endpoints.web.exposure.include` | Exposed Actuator endpoints (comma-separated) | Optional | `health,info` |

---

## Planned Configuration (Future Milestones)

The following properties represent planned configuration keys that are **not active in the current foundation**:

| Planned Variable | Purpose | Target Milestone |
| :--- | :--- | :--- |
| `OTLP_GRPC_PORT` | Listening port for OTLP gRPC telemetry | Milestone 1 (OTLP Receiver) |
| `OTLP_HTTP_PORT` | Listening port for OTLP HTTP/protobuf telemetry | Milestone 1 (OTLP Receiver) |
| `INGESTION_MAX_REQUEST_SIZE_BYTES` | Maximum payload size in bytes (default: 4MB) | Milestone 2 (Rate & Size Limits) |
| `INGESTION_RATE_LIMIT_RPS` | Maximum requests per second per tenant | Milestone 2 (Rate & Size Limits) |
| `CONTROL_PLANE_TOKEN_VERIFY_URL` | Control Plane internal token verification endpoint | Milestone 3 (Authentication) |
| `TELEMETRY_PIPELINE_BOOTSTRAP_SERVERS` | Downstream streaming broker bootstrap servers | Milestone 4 (Streaming Forwarder) |

---

## Configuration Profiles

- **`default` (`src/main/resources/application.yml`)**: Safe default settings. Sets graceful shutdown with a 20s timeout and conservative Actuator exposure (`health,info`).
- **`test` (`src/test/resources/application-test.yml`)**: Used during Maven test runs. Binds to random port `0`, disables startup banners, and exposes health details for test assertions.

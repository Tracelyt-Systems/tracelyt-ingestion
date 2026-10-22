# Architectural Boundaries & Separation of Concerns

This document details the boundary demarcation between **Tracelyt Ingestion** and neighboring services in the Tracelyt observability platform.

## Boundary Matrix

| Capability / Concern | Tracelyt Ingestion | Tracelyt Control Plane | Telemetry Processing Pipeline | Query & Storage Engine | Alerting Service |
| :--- | :---: | :---: | :---: | :---: | :---: |
| **OTLP Protocol Termination** | **Primary** | No | No | No | No |
| **Edge Payload Size & Rate Limits** | **Primary** | No | No | No | No |
| **Ingestion Token Extraction** | **Primary** | No | No | No | No |
| **Ingestion Token Validation (Authority)** | No | **Primary** | No | No | No |
| **Tenant & Organization Metadata** | No | **Primary** | No | No | No |
| **Collector Configuration Distribution** | No | **Primary** | No | No | No |
| **Audit Logs for Admin Changes** | No | **Primary** | No | No | No |
| **Telemetry Parsing & Enrichment** | No | No | **Primary** | No | No |
| **PII Redaction & Attribute Scrubbing** | No | No | **Primary** | No | No |
| **Downstream Stream Buffering (Kafka)** | Producer | No | Consumer | No | No |
| **Columnar Telemetry Persistence** | No | No | Ingestion Sink | **Primary** | No |
| **Analytical Queries (SQL / TraceQL)** | No | No | No | **Primary** | No |
| **Alert Evaluation & Threshold Checking** | No | No | Stream Consumer | Query Consumer | **Primary** |
| **Pager / Webhook Notification Delivery** | No | No | No | No | **Primary** |

---

## Detailed Responsibilities

### 1. What Belongs in Tracelyt Ingestion
- **Protocol Decoding**: Terminating HTTP and gRPC network connections carrying OpenTelemetry protobuf payloads.
- **Header Inspection**: Extracting tenant identifiers and bearer ingestion tokens from incoming request headers.
- **Edge Protective Filtering**: Rejecting payloads that exceed byte caps or arrive above tenant-allocated request rate limits.
- **Safe Protocol Acking**: Returning standard OTLP response codes (success, partial success, or resource exhausted) promptly without holding open long-lived connections.
- **Buffering / Handoff**: Forwarding parsed payloads onto streaming message buses for asynchronous processing.

### 2. What Explicitly Does Not Belong in Ingestion
- **Relational Storage**: Ingestion does not connect to PostgreSQL. It has no user tables, schema migrations, or JPA entities.
- **Complex Telemetry Transformation**: Ingestion does not calculate metric rollups, reconstruct trace trees, or execute heavy span transformations.
- **Direct Database Writing**: Ingestion must not write directly to analytical storage engines (e.g. ClickHouse) to avoid connection saturation and unbuffered write bursts.
- **User Authentication / Login**: Interactive developer logins, SSO, and user sessions belong exclusively in the Tracelyt Control Plane.

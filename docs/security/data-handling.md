# Telemetry Privacy & Data Handling Principles

> [!WARNING]
> **Design Intentions Notice**: The telemetry privacy, data minimization, and attribute redaction guidelines outlined below represent **architectural design requirements for future implementation**. Technical redaction and attribute filtering are not implemented in the current repository foundation.

## 1. Guiding Principles

1. **Data Minimization**: Ingestion gateways should accept only the telemetry attributes strictly necessary for observability, troubleshooting, and performance analysis.
2. **Zero Sensitive Payload Logging**: The ingestion service must never log full telemetry payload bodies, span attributes, or log message strings into its own operational logs.
3. **No Credential Harvesting**: Header inspection is limited to authorization tokens. Headers such as `Cookie`, `Set-Cookie`, `X-Auth-Token`, or basic auth credentials must never be copied into telemetry span attributes.

## 2. Planned Attribute Redaction Strategy

When telemetry processing logic is implemented in downstream components, the following categories of data must be identified and scrubbed:

- **Authentication Secrets**: Bearer tokens, API keys, password fields, private keys, session identifiers.
- **Personally Identifiable Information (PII)**: Credit card numbers (PCI-DSS), Social Security / National ID numbers, email addresses, phone numbers.
- **Sanitization Mechanism**: Downstream stream processors will apply configurable regex substitution rules (e.g., replacing matched strings with `[REDACTED]`).

## 3. Ingestion Gateway Operational Log Policy

To prevent the Ingestion service itself from becoming a vector for sensitive data leaks:
- Service operational logs (`logs/*.log`) are restricted to metadata: timestamp, log level, trace ID, tenant ID, request status, and byte size.
- Payload bodies received over OTLP/HTTP or OTLP/gRPC are **never logged at INFO or DEBUG levels**.

# Threat Model: Tracelyt Ingestion

This document establishes the initial threat model for the Tracelyt Ingestion service. It evaluates assets, trust boundaries, threat actors, abuse cases, and required security controls.

> [!NOTE]
> **Foundation Status**: Mitigations marked as *Active* are enforced in the current repository. Mitigations marked as *Required Before Endpoint Exposure* represent architectural controls that must be implemented before opening real telemetry receivers to network traffic.

---

## 1. Assets to Protect

1. **Internal Downstream Pipeline Availability**: Message brokers, stream processors, and columnar storage engines from denial of service.
2. **Tenant Data Confidentiality & Integrity**: Preventing cross-tenant eavesdropping, spoofing, or unauthorized telemetry injection.
3. **Control Plane Infrastructure**: Protecting token verification APIs from flooding or denial of service by ingestion traffic.
4. **Build and Supply Chain Integrity**: Protecting source code, build artifacts, and dependency trees from compromise.

---

## 2. Trust Boundaries

```
[ Untrusted Internet / Client Applications ]
------------------- Trust Boundary 1: Edge Ingress (TLS Termination) -------------------
[ Tracelyt Ingestion Gateway ]
------------------- Trust Boundary 2: Service-to-Service Network ------------------------
[ Tracelyt Control Plane (Token Verification) ]
------------------- Trust Boundary 3: Internal Data Pipeline ---------------------------
[ Streaming Message Broker (Kafka) & Stream Processors ]
```

- **Trust Boundary 1 (Edge Ingress)**: Untrusted traffic from application agents, OpenTelemetry collectors, and internet networks. All incoming data is unverified until authenticated and validated.
- **Trust Boundary 2 (Service Mesh / Internal)**: Synchronous communication between Ingestion and Control Plane for token verification and tenant quota resolution.
- **Trust Boundary 3 (Data Pipeline)**: Forwarding validated telemetry batches onto internal brokers.

---

## 3. Potential Threat Actors

- **External Malicious Actors**: Attempting DDoS attacks, credential stuffing, payload injection, or resource exhaustion.
- **Compromised Client Applications**: Legitimate client applications whose telemetry keys have leaked or been compromised.
- **Malicious / Compromised Contributors**: Attempting supply chain attacks through pull requests or dependency poisoning.
- **Curious / Malicious Tenants**: Attempting to inject telemetry claiming to originate from other organizations (cross-tenant spoofing).

---

## 4. Threat Matrix (STRIDE Analysis)

| # | Threat Scenario | STRIDE Category | Current Mitigation Status | Required Mitigation Before Endpoint Exposure |
| :- | :--- | :--- | :--- | :--- |
| **1** | **Secret leakage via Git or CI logs** | Information Disclosure | **Active**: `.gitignore` blocks `.env`, keys, certs. CI does not dump env or secrets. | Pre-commit secret scanning hooks and automated secret scanning alerts. |
| **2** | **Compromised dependencies / Supply chain attack** | Tampering | **Active**: Pinned actions with commit SHAs, SpotBugs analysis, Checkstyle, Renovate config. | Automated dependency vulnerability gating in CI. |
| **3** | **Unauthenticated telemetry submission** | Spoofing / Repudiation | *Unresolved (No endpoints exist)* | Mandatory Bearer token validation against Control Plane with caching. |
| **4** | **Cross-tenant data injection** | Spoofing / Elevation of Privilege | *Unresolved (No endpoints exist)* | Cryptographic tenant binding: tokens are permanently scoped to specific tenant IDs. |
| **5** | **Oversized or malformed payloads** | Denial of Service | *Unresolved (No endpoints exist)* | Maximum payload limit (4MB default), strict protobuf schema framing checks. |
| **6** | **Cardinality & volume exhaustion (DDoS)** | Denial of Service | *Unresolved (No endpoints exist)* | Token bucket rate limiting, tenant-level ingestion quotas, backpressure shedding. |
| **7** | **Sensitive data (PII/secrets) in telemetry** | Information Disclosure | *Documented Rule Only* | Downstream stream processor redaction and regex masking rules. |
| **8** | **Public exposure of Actuator diagnostics** | Information Disclosure | **Active**: Management endpoints restricted to `health,info`; details hidden unless authorized. | Network firewalling; keep Actuator on internal loopback / cluster IP. |
| **9** | **Container privilege escalation** | Elevation of Privilege | **Active**: Multi-stage build runs as non-root user (`tracelyt` UID 10001). | Read-only root filesystem and Linux capability dropping in deployment manifests. |
| **10** | **Misleading health / readiness status** | Denial of Service / Repudiation | **Active**: Documented distinction between process liveness and ingestion readiness. | Dynamic readiness probe linked to downstream broker connectivity and queue depth. |

---

## 5. Security Requirements for Phase 1 Ingestion Receivers

Before opening any HTTP or gRPC receiver:
1. **Authentication Mandatory**: Reject requests lacking valid authentication headers before parsing payload bodies.
2. **Byte Limits Enforced Before Deserialization**: Reject HTTP requests with `Content-Length` exceeding configured maximums immediately with HTTP 413.
3. **No Dynamic Deserialization**: Use static protobuf schemas; reject unknown or untyped extensions.
4. **Rate Limiting at Edge**: Apply per-tenant rate limits with HTTP 429 / gRPC `RESOURCE_EXHAUSTED` responses.

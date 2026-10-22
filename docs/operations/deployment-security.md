# Deployment Security Guidelines

> [!CAUTION]
> **Pre-Release Notice**: Tracelyt Ingestion in its current state is an initial foundation and **must not be deployed to public or production environments as an operational gateway**. The protections described below are mandatory prerequisites for future deployment phases.

## 1. Network & Ingress Security

Before any telemetry endpoint is exposed to network traffic:
- **TLS 1.3 Termination**: Ingress proxies or API gateways (e.g., Envoy, Traefik, NGINX) must terminate TLS using valid certificates. Plaintext HTTP/gRPC telemetry must never be accepted over untrusted public networks.
- **Port Isolation**: Operational Actuator endpoints (port `8081` `/actuator/**`) must remain bound to internal cluster networks or loopback interfaces. Never expose Actuator endpoints to the public internet.

## 2. Container Runtime Security

The provided [Dockerfile](../../Dockerfile) adheres to container security practices:
- **Non-Root User**: Runs as `tracelyt` (UID 10001, GID 10001) with no root privileges.
- **Read-Only Root Filesystem**: Deployments should mount the container root filesystem as read-only (`readOnlyRootFilesystem: true` in Kubernetes) with temporary storage mounted at `/tmp`.
- **Capability Dropping**: Drop all Linux capabilities (`drop: ["ALL"]`).
- **No Host Mounts**: Never mount the host Docker socket (`/var/run/docker.sock`) or host filesystem into the container.

## 3. Resource Allocation & Limits

When deploying to container orchestrators:
- **CPU & Memory Limits**: Define strict memory requests and limits. The container runtime is configured with `-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0`.
- **Ephemeral Storage Limits**: Bound scratch storage to prevent unbounded disk filling from JVM heap dumps or temporary files.

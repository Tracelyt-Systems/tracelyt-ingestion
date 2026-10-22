# Local Development Setup Guide

This guide walks you through setting up your local workstation to develop, build, and run the Tracelyt Ingestion service.

## Prerequisites

| Requirement | Supported Version | Purpose |
| :--- | :--- | :--- |
| **Java JDK** | 21 LTS | Java compiler and runtime (Temurin, Corretto, or Microsoft OpenJDK) |
| **Git** | 2.30+ | Version control |
| **Docker** | Latest Stable | Optional: for building and running the containerized service |

*Note: You do not need to install Apache Maven globally; use the included official wrapper (`mvnw` / `mvnw.cmd`).*

## Step 1: Clone the Repository

```bash
git clone https://github.com/Tracelyt-Systems/tracelyt-ingestion.git
cd tracelyt-ingestion
```

Verify your active Java version:
```bash
java -version
# Expected: openjdk version "21.x.y"
```

## Step 2: Environment Configuration

Copy the sample environment file to create your local `.env`:

```bash
# Linux / macOS
cp .env.example .env

# Windows (PowerShell)
Copy-Item .env.example .env
```

The default port is configured to `8081` so that Tracelyt Ingestion can run concurrently alongside the Tracelyt Control Plane (port `8080`) on the same host without port conflicts.

## Step 3: Build & Static Analysis

Execute the full build, style checks, and SpotBugs static analysis:

### Linux / macOS
```bash
./mvnw clean verify
```

### Windows (PowerShell)
```powershell
.\mvnw.cmd clean verify
```

To run individual quality checks:
```bash
# Style checking
./mvnw checkstyle:check

# SpotBugs bytecode analysis
./mvnw spotbugs:check
```

## Step 4: Run the Application Locally

Start the Spring Boot application:

### Linux / macOS
```bash
./mvnw spring-boot:run
```

### Windows (PowerShell)
```powershell
.\mvnw.cmd spring-boot:run
```

The application will bind to port `8081`.

## Step 5: Verify Health Diagnostics

In a separate terminal, query the operational health endpoints:

```bash
# Check process health
curl http://localhost:8081/actuator/health

# Response:
# {"status":"UP"}

# Application metadata
curl http://localhost:8081/actuator/info
```

> [!NOTE]
> The application currently exposes operational management endpoints only. OTLP telemetry ingestion endpoints will be added in subsequent implementation milestones.

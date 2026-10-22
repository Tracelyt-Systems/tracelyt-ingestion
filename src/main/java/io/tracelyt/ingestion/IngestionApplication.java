package io.tracelyt.ingestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for Tracelyt Ingestion service.
 *
 * <p>Serves as the foundation for the telemetry ingestion component of the Tracelyt
 * observability platform. At this foundation stage, only the application lifecycle
 * and operational health diagnostics are active; no telemetry endpoints or ingestion
 * pipelines are implemented.
 */
@SpringBootApplication
public class IngestionApplication {

    public static void main(String[] args) {
        SpringApplication.run(IngestionApplication.class, args);
    }
}

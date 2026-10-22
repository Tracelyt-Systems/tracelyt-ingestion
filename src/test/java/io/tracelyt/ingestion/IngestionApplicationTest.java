package io.tracelyt.ingestion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Baseline context startup smoke test for Tracelyt Ingestion.
 *
 * <p>Verifies that the Spring Boot application context bootstraps cleanly without errors.
 */
@SpringBootTest
@ActiveProfiles("test")
class IngestionApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("Application context initializes successfully")
    void contextLoads() {
        assertThat(applicationContext).isNotNull();
    }
}

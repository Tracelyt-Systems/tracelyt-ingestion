# syntax=docker/dockerfile:1

# Stage 1: Build the executable application JAR
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /workspace

# Cache dependencies layer
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline --no-transfer-progress

# Build application
COPY src/ src/
RUN ./mvnw package -DskipTests --no-transfer-progress

# Stage 2: Minimal runtime image
FROM eclipse-temurin:21-jre-jammy AS runner

# Create dedicated non-root user and group
RUN groupadd -g 10001 tracelyt && \
    useradd -u 10001 -g tracelyt -m -s /bin/bash tracelyt

WORKDIR /app

# Copy built artifact
COPY --from=builder --chown=tracelyt:tracelyt /workspace/target/tracelyt-ingestion-*.jar app.jar

USER tracelyt:tracelyt

EXPOSE 8081

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]

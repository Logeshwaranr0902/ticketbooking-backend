# Build stage
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY theater/pom.xml theater/
RUN ./mvnw dependency:go-offline -pl theater -am -DskipTests -q || true
COPY theater/src theater/src
RUN ./mvnw package -pl theater -am -DskipTests -q

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/theater/target/*.jar app.jar
EXPOSE 8080
ENV PORT=8080
ENTRYPOINT ["java", "-jar", "app.jar"]

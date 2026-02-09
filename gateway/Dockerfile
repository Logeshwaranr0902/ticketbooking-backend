# Build stage
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY gateway/pom.xml gateway/
RUN ./mvnw dependency:go-offline -pl gateway -am -DskipTests -q || true
COPY gateway/src gateway/src
RUN ./mvnw package -pl gateway -am -DskipTests -q

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/gateway/target/*.jar app.jar
EXPOSE 8080
ENV PORT=8080
ENTRYPOINT ["java", "-jar", "app.jar"]

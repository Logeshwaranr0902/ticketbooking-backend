# Build stage
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY movie/pom.xml movie/
RUN ./mvnw dependency:go-offline -pl movie -am -DskipTests -q || true
COPY movie/src movie/src
RUN ./mvnw package -pl movie -am -DskipTests -q

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/movie/target/*.jar app.jar
EXPOSE 8080
ENV PORT=8080
ENTRYPOINT ["java", "-jar", "app.jar"]

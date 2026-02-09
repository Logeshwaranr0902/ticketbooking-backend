# Build stage
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY booking/pom.xml booking/
RUN ./mvnw dependency:go-offline -pl booking -am -DskipTests -q || true
COPY booking/src booking/src
RUN ./mvnw package -pl booking -am -DskipTests -q

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/booking/target/*.jar app.jar
EXPOSE 8080
ENV PORT=8080
ENTRYPOINT ["java", "-jar", "app.jar"]

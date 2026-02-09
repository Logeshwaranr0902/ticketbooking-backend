# Build stage
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY show/pom.xml show/
RUN ./mvnw dependency:go-offline -pl show -am -DskipTests -q || true
COPY show/src show/src
RUN ./mvnw package -pl show -am -DskipTests -q

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/show/target/*.jar app.jar
EXPOSE 8080
ENV PORT=8080
ENTRYPOINT ["java", "-jar", "app.jar"]

FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="ticketbooking"
WORKDIR /app
COPY target/movie-1.0-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]

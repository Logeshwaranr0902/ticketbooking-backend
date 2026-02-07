FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="ticketbooking"
WORKDIR /app
COPY target/theater-1.0-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]

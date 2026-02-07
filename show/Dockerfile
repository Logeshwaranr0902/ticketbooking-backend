FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="ticketbooking"
WORKDIR /app
COPY target/show-1.0-SNAPSHOT.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]

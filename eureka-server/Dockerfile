FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="ticketbooking"
WORKDIR /app
COPY target/eureka-server-1.0-SNAPSHOT.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]

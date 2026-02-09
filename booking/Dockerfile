FROM maven:3.9.6-eclipse-temurin-21
WORKDIR /app
COPY . .
RUN mvn package -pl booking -am -DskipTests -q
ENTRYPOINT ["java", "-jar", "booking/target/booking-1.0-SNAPSHOT.jar"]

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY . .
RUN ./mvnw package -pl booking -am -DskipTests -q
ENTRYPOINT ["java", "-jar", "booking/target/booking-1.0-SNAPSHOT.jar"]

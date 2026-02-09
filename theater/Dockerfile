FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY . .
RUN ./mvnw package -pl theater -am -DskipTests -q
ENTRYPOINT ["java", "-jar", "theater/target/theater-1.0-SNAPSHOT.jar"]

FROM maven:3.9.6-eclipse-temurin-21
WORKDIR /app
COPY . .
RUN mvn package -pl theater -am -DskipTests -q
ENTRYPOINT ["java", "-jar", "theater/target/theater-1.0-SNAPSHOT.jar"]

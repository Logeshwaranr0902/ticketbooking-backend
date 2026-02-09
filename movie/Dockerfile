FROM maven:3.9.6-eclipse-temurin-21
WORKDIR /app
COPY . .
RUN mvn package -pl movie -am -DskipTests -q
ENTRYPOINT ["java", "-jar", "movie/target/movie-1.0-SNAPSHOT.jar"]

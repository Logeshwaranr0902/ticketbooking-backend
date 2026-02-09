FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw package -pl movie -am -DskipTests -q
ENTRYPOINT ["java", "-jar", "movie/target/movie-1.0-SNAPSHOT.jar"]

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw package -pl show -am -DskipTests -q
ENTRYPOINT ["java", "-jar", "show/target/show-1.0-SNAPSHOT.jar"]

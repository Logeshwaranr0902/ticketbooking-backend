FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY . .
RUN ./mvnw package -pl gateway -am -DskipTests -q
ENTRYPOINT ["java", "-jar", "gateway/target/gateway-1.0-SNAPSHOT.jar"]

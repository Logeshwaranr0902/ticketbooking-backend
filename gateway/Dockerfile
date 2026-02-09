FROM maven:3.9.6-eclipse-temurin-21
WORKDIR /app
COPY . .
RUN mvn package -pl gateway -am -DskipTests -q
ENTRYPOINT ["java", "-jar", "gateway/target/gateway-1.0-SNAPSHOT.jar"]

FROM maven:3.9.6-eclipse-temurin-21
WORKDIR /app
COPY . .
RUN mvn package -pl show -am -DskipTests -q
ENTRYPOINT ["java", "-jar", "show/target/show-1.0-SNAPSHOT.jar"]

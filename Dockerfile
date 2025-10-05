
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src src

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java","-jar","target/InventoryManagement-0.0.1-SNAPSHOT.jar"]
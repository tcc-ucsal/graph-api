# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install || { echo 'Maven build failed'; exit 1; }

# Stage 2: Run the application
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/graph-api-0.1.0.jar ./graph-api.jar
EXPOSE 8080
CMD ["java", "-Dspring.profiles.active=dev", "-jar", "graph-api.jar"]
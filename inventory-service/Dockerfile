## Development & Test level Dockerfile
## Build (Maven kullanarak JAR üretir):
#FROM maven:3.9-eclipse-temurin-21-alpine AS build
#WORKDIR /app
#COPY . .
## RUN mvn clean package -DskipTests

## Run (Sadece JAR dosyasını çalıştırır)
#FROM eclipse-temurin:21-jdk-alpine
#WORKDIR /app
## Build aşamasında üretilen JAR dosyasını kopyalar:
#COPY --from=build /app/target/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]

# Production level Dockerfile
# 1. Aşama: Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Aşama: Run
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

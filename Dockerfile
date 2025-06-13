# Stage 1: Build do projeto com Maven
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

# Copia o pom e o código fonte
COPY pom.xml .
COPY src ./src

# Faz o build e gera o jar
RUN mvn clean package -DskipTests

# Stage 2: Imagem final para rodar o jar
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia o jar do stage build para cá
COPY --from=build /app/target/questionarios-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

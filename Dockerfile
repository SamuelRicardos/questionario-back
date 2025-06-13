# Use uma imagem oficial do Java como base
FROM openjdk:17-jdk-slim

# Crie um diretório de trabalho dentro do container
WORKDIR /app

# Copie o jar do projeto para o container
COPY target/questionarios-0.0.1-SNAPSHOT.jar app.jar

# Exponha a porta do Spring Boot
EXPOSE 8080

# Comando para rodar o jar
ENTRYPOINT ["java", "-jar", "app.jar"]
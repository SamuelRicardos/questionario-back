# Etapa de build com Maven
FROM maven:3.9.6-eclipse-temurin-17 as build
WORKDIR /app

# Copia todos os arquivos do projeto para dentro do container
COPY . .

# Executa o build sem os testes
RUN mvn clean install -DskipTests

# Etapa de runtime com JDK
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=build /app/target/questionarios-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta 8080
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]

# Estágio 1: Build (O Docker vai baixar o Maven e compilar seu código)
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Estágio 2: Runtime (O Docker vai criar uma imagem leve só com o necessário)
FROM eclipse-temurin:21-jre
WORKDIR /app
# Aqui pegamos o arquivo gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
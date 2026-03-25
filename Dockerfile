FROM eclipse-temurin:21-jre

# Cria uma pasta de trabalho dentro do container
WORKDIR /app

# Copia o arquivo .jar gerado pelo Maven para dentro do container
COPY target/*.jar app.jar

# Expõe a porta 8080 (a mesma do Tomcat)
EXPOSE 8080

# Comando para rodar a aplicação quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]
# Usa uma imagem base com Java
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho no contêiner
WORKDIR /app

# Copia o arquivo JAR do microserviço para o contêiner
COPY target/serviceLending-0.0.1-SNAPSHOT.jar app.jar

# Define a porta que o microserviço vai usar
EXPOSE 8083

# Comando para iniciar o microserviço
CMD ["java", "-jar", "app.jar"]

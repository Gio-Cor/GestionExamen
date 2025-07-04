FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/GestionInventarioNuevo-0.0.1.jar
COPY ${JAR_FILE} app_gestion_inventario.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_gestion_inventario.jar"]
FROM openjdk:17-alpine AS builder

WORKDIR /build

# Copia específicamente el JAR con su nombre completo
COPY target/api-reservations-0.0.1-SNAPSHOT.jar  app.jar

# Extraer las capas del JAR usando el nombre específico
RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:17-alpine

WORKDIR /app

# Copiar las capas extraídas con rutas absolutas
COPY --from=builder /build/dependencies/ ./
COPY --from=builder /build/spring-boot-loader/ ./
COPY --from=builder /build/snapshot-dependencies/ ./
COPY --from=builder /build/application/ ./

EXPOSE 8080

# Punto de entrada para la aplicación
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher", "--spring.config.location=classpath:/application.yml"]
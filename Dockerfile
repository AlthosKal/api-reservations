# Etapa de construcción (Builder)
# Usamos una imagen de OpenJDK 17 basada en Alpine Linux para la etapa de construcción
FROM openjdk:17-alpine AS builder

# Establecemos el directorio de trabajo en /build dentro del contenedor
WORKDIR /build

# Copia específicamente el JAR generado en el directorio target con su nombre completo
# Copia el archivo JAR desde el host al contenedor como app.jar
COPY target/api-reservations-0.0.1-SNAPSHOT.jar app.jar

# Extraer las capas del JAR usando el nombre específico
# Utiliza el modo 'layertools' de Spring Boot para extraer las capas del archivo JAR
RUN java -Djarmode=layertools -jar app.jar extract  \

# Etapa de ejecución
# Usamos una nueva imagen de OpenJDK 17 basada en Alpine Linux para la etapa de ejecución
FROM openjdk:17-alpine

# Establecemos el directorio de trabajo en /app dentro del contenedor
WORKDIR /app

# Copia las dependencias del JAR extraído a /app/dependencies
COPY --from=builder /build/dependencies/ ./
# Copia los archivos relacionados con el cargador de Spring Boot
COPY --from=builder /build/spring-boot-loader/ ./
# Copia las dependencias adicionales
COPY --from=builder /build/snapshot-dependencies/ ./
# Copia la configuración y otros archivos de la aplicación
COPY --from=builder /build/application/ ./
# Copiar las capas extraídas con rutas absolutas desde la etapa 'builder'

EXPOSE 8080  # Expone el puerto 8080 para que sea accesible desde fuera del contenedor

# Punto de entrada para la aplicación: Inicia la aplicación Spring Boot con el cargador de JAR
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher", "--spring.config.location=classpath:/application.yml"]
# El comando ejecutará la aplicación Spring Boot con la configuración del archivo 'application.yml' localizado dentro del contenedor

package com.example.api_reservations.connector.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

// Indica que esta clase es una configuración de Spring
@Configuration
// Permite la carga de propiedades desde el archivo de configuración con el prefijo "http-connector"
@ConfigurationProperties(prefix = "http-connector")
@Data // Genera automáticamente getters, setters y otros métodos útiles con Lombok
public class HttpConnectorConfiguration {

    // Mapa que almacena configuraciones de distintos hosts identificados por una clave String
    private HashMap<String, HostConfiguration> hosts;

    @Data
    public static class HostConfiguration {
        private String host; // Dirección del host
        private int port; // Puerto del host
        // Mapa de configuraciones de endpoints, identificados por un String
        private HashMap<String, EndpointConfiguration> endpoints;
    }

    @Data
    public static class EndpointConfiguration {
        private String url; // URL del endpoint
        private long readTimeout; // Tiempo máximo de espera para leer una respuesta
        private long writeTimeout; // Tiempo máximo de espera para escribir una solicitud
        private long connectTimeout; // Tiempo máximo de espera para establecer conexión
    }
}
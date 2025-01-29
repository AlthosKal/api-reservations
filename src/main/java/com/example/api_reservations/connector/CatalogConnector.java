package com.example.api_reservations.connector;

import com.example.api_reservations.connector.configuration.HttpConnectorConfiguration;
import com.example.api_reservations.connector.response.CityDTO;
import com.example.api_reservations.exception.CatalogClientException;
import com.example.api_reservations.exception.CatalogServerException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

// Indica que esta clase es un componente de Spring que puede ser inyectado en otros lugares
@Component
public class CatalogConnector {

    private final WebClient webClient; // Cliente HTTP reactivo
    private static final Logger log = LoggerFactory.getLogger(CatalogConnector.class);

    // Constructor que configura el WebClient con base en la configuración proporcionada
    public CatalogConnector(@Value("${catalog.service.url}") String catalogServiceUrl,
                            HttpConnectorConfiguration configuration) {

        // Obtiene la configuración del host "api-catalog"
        HttpConnectorConfiguration.HostConfiguration hostConfig = configuration.getHosts().get("api-catalog");
        // Obtiene la configuración del endpoint "get-city"
        HttpConnectorConfiguration.EndpointConfiguration endpointConfig = hostConfig.getEndpoints().get("get-city");

        // Configura el cliente HTTP con los tiempos de espera adecuados
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Math.toIntExact(endpointConfig.getConnectTimeout()))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(endpointConfig.getReadTimeout(), TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(endpointConfig.getWriteTimeout(), TimeUnit.MILLISECONDS)));

        // Configura WebClient con la URL base y los encabezados adecuados
        this.webClient = WebClient.builder()
                .baseUrl(catalogServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    // Método para obtener la información de una ciudad utilizando un patrón Circuit Breaker
    @CircuitBreaker(name = "api-catalog", fallbackMethod = "fallbackGetCity")
    public Mono<CityDTO> getCityDTO(String code) {
        return webClient.get()
                .uri("/city/{code}", code)
                .retrieve()
                // Manejo de errores para respuestas 4XX
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new CatalogClientException("City not found with code: " + code)))
                // Manejo de errores para respuestas 5XX
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new CatalogServerException("Catalog service error")))
                .bodyToMono(CityDTO.class)
                // Registra errores en el log
                .doOnError(error -> log.error("Error fetching city with code {}: {}", code, error.getMessage()));
    }

    // Método bloqueante para obtener la información de una ciudad (útil en casos necesarios)
    public CityDTO getCityDTOBlocking(String code) {
        return getCityDTO(code).block();
    }

    // Método de fallback en caso de fallo en el Circuit Breaker
    private Mono<CityDTO> getCityFallback(String code, Throwable throwable) {
        if (throwable instanceof CatalogClientException) {
            log.warn("Fallback for client exception: {}", throwable.getMessage());
        } else if (throwable instanceof CatalogServerException) {
            log.error("Fallback for server exception: {}", throwable.getMessage());
        } else {
            log.error("Unexpected error in fallback: {}", throwable.getMessage());
        }
        return Mono.empty(); // Devuelve un valor vacío o un valor predeterminado
    }
}

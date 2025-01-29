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

@Component
public class CatalogConnector {

    private final WebClient webClient;

    private static final Logger log = LoggerFactory.getLogger(CatalogConnector.class);

    public CatalogConnector(@Value("${catalog.service.url}") String catalogServiceUrl,
            HttpConnectorConfiguration configuration) {

        HttpConnectorConfiguration.HostConfiguration hostConfig = configuration.getHosts().get("api-catalog");

        HttpConnectorConfiguration.EndpointConfiguration endpointConfig = hostConfig.getEndpoints().get("get-city");

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Math.toIntExact(endpointConfig.getConnectTimeout()))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(endpointConfig.getReadTimeout(), TimeUnit.MILLISECONDS))
                        .addHandlerLast(
                                new WriteTimeoutHandler(endpointConfig.getWriteTimeout(), TimeUnit.MILLISECONDS)));

        this.webClient = WebClient.builder().baseUrl(catalogServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient)).build();
    }

    @CircuitBreaker(name = "api-catalog", fallbackMethod = "fallbackGetCity")
    public Mono<CityDTO> getCityDTO(String code) {
        return webClient.get().uri("/city/{code}", code).retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new CatalogClientException("City not found with code: " + code)))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new CatalogServerException("Catalog service error")))
                .bodyToMono(CityDTO.class)
                .doOnError(error -> log.error("Error fetching city with code {}: {}", code, error.getMessage()));
    }

    // For cases where blocking is absolutely necessary
    public CityDTO getCityDTOBlocking(String code) {
        return getCityDTO(code).block();
    }

    private Mono<CityDTO> getCityFallback(String code, Throwable throwable) {
        if (throwable instanceof CatalogClientException) {
            log.warn("Fallback for client exception: {}", throwable.getMessage());
        } else if (throwable instanceof CatalogServerException) {
            log.error("Fallback for server exception: {}", throwable.getMessage());
        } else {
            log.error("Unexpected error in fallback: {}", throwable.getMessage());
        }
        return Mono.empty(); // O devuelve un valor predeterminado
    }

}
package com.example.api_reservations.connector;

import com.example.api_reservations.connector.response.CityDTO;
import com.example.api_reservations.exception.CatalogClientException;
import com.example.api_reservations.exception.CatalogServerException;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Component
public class CatalogConnector {

    private final WebClient webClient;

    public CatalogConnector(@Value("${catalog.service.url}") String catalogServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(catalogServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE)))
                .build();
    }

    public Mono<CityDTO> getCityDTO(String code) {
        return webClient.get()
                .uri("/city/{code}", code)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new CatalogClientException("City not found with code: " + code)))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new CatalogServerException("Catalog service error")))
                .bodyToMono(CityDTO.class)
                .doOnError(error -> log.error("Error fetching city with code {}: {}", code, error.getMessage()));
    }

    // For cases where blocking is absolutely necessary
    public CityDTO getCityDTOBlocking(String code) {
        return getCityDTO(code)
                .block();
    }
}
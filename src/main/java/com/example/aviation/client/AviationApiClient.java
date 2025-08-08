package com.example.aviation.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
@Slf4j
public class AviationApiClient {

    private final WebClient webClient;

    @Value("${aviationapi.base-url:https://api.aviationapi.com}")
    private String baseUrl;

    @Retry(name = "aviationApi")
    @CircuitBreaker(name = "aviationApi", fallbackMethod = "fallback")
    public Mono<String> fetchAirportJson(String icao) {
        log.info("Fetching airport details for ICAO: {}", icao);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v1/airports")
                        .queryParam("apt", icao)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
    }

    private Mono<String> fallback(String icao, Throwable ex) {
        log.warn("Fallback triggered for ICAO {} due to: {}", icao, ex.getMessage());
        return Mono.error(new RuntimeException("Upstream unavailable: " + ex.getMessage()));
    }
}

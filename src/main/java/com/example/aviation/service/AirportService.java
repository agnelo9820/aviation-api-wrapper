package com.example.aviation.service;

import com.example.aviation.client.AviationApiClient;
import com.example.aviation.dto.Airport;
import com.example.aviation.dto.AirportResponse;
import com.example.aviation.exception.ExternalServiceException;
import com.example.aviation.mapper.AirportMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AirportService {

    private final AviationApiClient client;
    private final ObjectMapper objectMapper;
    private final AirportMapper airportMapper;

    public Mono<AirportResponse> getAirportByIcao(String icao) {
        return client.fetchAirportJson(icao)
                .map(json -> {
                    if (json == null || json.isBlank()) {
                        throw new ExternalServiceException("Empty response from upstream");
                    }
                    return getAirportResponse(icao, json);
                })
                .onErrorMap(ex -> new ExternalServiceException("Failed to fetch airport info: " + ex.getMessage()));
    }


    private AirportResponse getAirportResponse(String icao, String json) {
        try {
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            Map<String, List<Airport>> data = objectMapper.readValue(json, new TypeReference<>() {
            });
            List<Airport> airports = data.get(icao);
            if (airports != null && !airports.isEmpty())
                return airportMapper.toDto(airports.get(0));
            else
                return new AirportResponse();
        } catch (JsonProcessingException e) {
            throw new ExternalServiceException("Invalid response from upstream");
        }
    }

}

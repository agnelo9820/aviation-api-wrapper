package com.example.aviation.controller;

import com.example.aviation.dto.AirportResponse;
import com.example.aviation.service.AirportService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/api/v1/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @GetMapping("/{icao}")
    public Mono<AirportResponse> getByIcao(
            @PathVariable("icao") @Pattern(regexp = "^[A-Za-z]{4}$", message = "ICAO must be 4 letters") String icao) {
        return airportService.getAirportByIcao(icao.toUpperCase());
    }
}

package com.example.aviation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirportResponse {

    private String name;
    private String city;
    private String state;
    private String county;
    private String icao;
    private String iata;
    private String latitude;
    private String longitude;

}

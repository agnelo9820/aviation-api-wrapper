package com.example.aviation.mapper;

import com.example.aviation.dto.Airport;
import com.example.aviation.dto.AirportResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AirportMapper {

    @Mapping(target = "name", source = "facilityName")
    @Mapping(target = "icao", source = "icaoIdent")
    @Mapping(target = "iata", source = "faaIdent")
    AirportResponse toDto(Airport airport);

}

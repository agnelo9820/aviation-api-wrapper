package com.example.aviation;

import com.example.aviation.client.AviationApiClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AirportControllerIT {

    @Autowired
    WebTestClient webTestClient;

    @MockitoBean
    AviationApiClient apiClient;

    @Test
    void whenIcaoExists_thenReturnAirport() {
        String sampleJson = """
                {
                    "KATL": [
                        {
                            "site_number": "03640.*A",
                            "type": "AIRPORT",
                            "facility_name": "HARTSFIELD - JACKSON ATLANTA INTL",
                            "faa_ident": "ATL",
                            "icao_ident": "KATL",
                            "region": "ASO",
                            "district_office": "ATL",
                            "state": "GA",
                            "state_full": "GEORGIA",
                            "county": "FULTON",
                            "city": "ATLANTA",
                            "ownership": "PU",
                            "use": "PU",
                            "manager": "BALRAM BHEODARI",
                            "manager_phone": "404-530-6600",
                            "latitude": "33-38-12.1186N",
                            "latitude_sec": "121092.1186N",
                            "longitude": "084-25-40.3104W",
                            "longitude_sec": "303940.3104W",
                            "elevation": "1026",
                            "magnetic_variation": "05W",
                            "tpa": "",
                            "vfr_sectional": "ATLANTA",
                            "boundary_artcc": "ZTL",
                            "boundary_artcc_name": "ATLANTA",
                            "responsible_artcc": "ZTL",
                            "responsible_artcc_name": "ATLANTA",
                            "fss_phone_number": "",
                            "fss_phone_numer_tollfree": "1-800-WX-BRIEF",
                            "notam_facility_ident": "ATL",
                            "status": "O",
                            "certification_typedate": "I E S 05/1973",
                            "customs_airport_of_entry": "N",
                            "military_joint_use": "N",
                            "military_landing": "Y",
                            "lighting_schedule": "",
                            "beacon_schedule": "SS-SR",
                            "control_tower": "Y",
                            "unicom": "122.950",
                            "ctaf": "",
                            "effective_date": "11/04/2021"
                        }
                    ]
                }
                """;
        Mockito.when(apiClient.fetchAirportJson("KATL")).thenReturn(Mono.just(sampleJson));

        webTestClient.get().uri("/api/v1/airports/KATL")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.icao").isEqualTo("KATL")
                .jsonPath("$.iata").isEqualTo("ATL")
                .jsonPath("$.name").isEqualTo("HARTSFIELD - JACKSON ATLANTA INTL");
    }
}

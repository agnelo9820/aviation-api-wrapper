# Aviation API Wrapper

This is a sample Java + Spring Boot microservice that wraps an upstream aviation data API (https://api.aviationapi.com) to fetch airport details by ICAO code.

## Features
- `GET /api/v1/airports/{icao}` - lookup airport by ICAO (4-letter code)
- Uses **WebClient** for external calls
- Resilience via **Resilience4j** (retry + circuit breaker)
- Basic validation and global error handling
- Ready for containerization (Dockerfile included)

## Run locally
Requirements: Java 17+, Maven

```bash
mvn clean package
mvn spring-boot:run
# service will run on http://localhost:8080
```

## Example
```
GET http://localhost:8080/api/v1/airports/KJFK
```

## Tests
Run:
```bash
mvn test
```

## Notes / Assumptions
- The project focuses on structure, resilience, and clear layering.
- The client expects the aviationapi JSON under `/v1/airports?apt={icao}`. Adapt the client if upstream differs.
- AI tools were used to help scaffold example code and README; all code should be reviewed before use.


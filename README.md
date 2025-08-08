# Aviation API Wrapper - Backend Engineer Assignment



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


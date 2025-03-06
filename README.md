# Calculator and REST with Spring Boot and Kafka

## Requirements
- [Docker](https://www.docker.com/)
- [Postman](https://www.postman.com/downloads/) (or similar)

## Setup environment
### Using Docker

1. Create the .jar files
   - On Windows

    ```cmd
    .\mvnw.cmd clean package
    ```

   - On Linux/MacOS, if Maven is installed

    ```bash
    mvn clean package
    ```
    - If it's not installed

    ```bash
    ./mvnw clean package
    ```

2. Build the Docker images
    ```cmd
    docker compose build -no--cache
    ```

3. Launch the containers
    ```cmd
    docker compose up -d
    ```

## REST Endpoints

- **Base URL:** localhost:8080

### GET /sum

| Parameter | Type   | Description   |
|-----------|--------|---------------|
| a         | number | First addend  |
| b         | number | Second addend |

### GET /subtract

| Parameter | Type   | Description |
|-----------|--------|-------------|
| a         | number | Minuend     |
| b         | number | Subtrahend  |

### GET /multiply

| Parameter | Type   | Description   |
|-----------|--------|---------------|
| a         | number | First factor  |
| b         | number | Second factor |

### GET /divide

| Parameter | Type   | Description |
|-----------|--------|-------------|
| a         | number | Dividend    |
| b         | number | Divisor     |


package com.example.rest.controllers;

import com.example.rest.dtos.CalculationRequestDTO;
import com.example.rest.dtos.CalculationResponseDTO;
import com.example.rest.middleware.kafka.CalculatorProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class CalculatorController {

    private static final Logger log = LoggerFactory.getLogger(CalculatorController.class);

    private final CalculatorProducer calculatorProducer;

    @Autowired
    public CalculatorController(CalculatorProducer calculatorProducer) {
        this.calculatorProducer = calculatorProducer;
    }

    @GetMapping("/sum")
    public ResponseEntity<CalculationResponseDTO> sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b,
                                                      @RequestHeader(value = "X-Request-Id", required = false) String requestId) {
        setXRequestIdHeader(requestId);
        log.info("X-Request-Id {} | sum of {} and {}", MDC.get("X-Request-Id"), a, b);

        CalculationRequestDTO calculationRequestDTO = new CalculationRequestDTO("sum", a, b);
        return getCalculationResponseDTOResponseEntity(calculationRequestDTO);
    }

    @GetMapping("/subtract")
    public ResponseEntity<CalculationResponseDTO> subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b,
                                                           @RequestHeader(value = "X-Request-Id", required = false) String requestId) {
        setXRequestIdHeader(requestId);
        log.info("X-Request-Id {} | subtract of {} and {}", MDC.get("X-Request-Id"), a, b);

        CalculationRequestDTO calculationRequestDTO = new CalculationRequestDTO("subtract", a, b);
        return getCalculationResponseDTOResponseEntity(calculationRequestDTO);
    }

    @GetMapping("/multiply")
    public ResponseEntity<CalculationResponseDTO> multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b,
                                                           @RequestHeader(value = "X-Request-Id", required = false) String requestId) {
        setXRequestIdHeader(requestId);
        log.info("X-Request-Id {} | multiply of {} and {}", MDC.get("X-Request-Id"), a, b);

        CalculationRequestDTO calculationRequestDTO = new CalculationRequestDTO("multiply", a, b);
        return getCalculationResponseDTOResponseEntity(calculationRequestDTO);
    }

    @GetMapping("/divide")
    public ResponseEntity<CalculationResponseDTO> divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b,
                                                         @RequestHeader(value = "X-Request-Id", required = false) String requestId) {
        setXRequestIdHeader(requestId);
        log.info("X-Request-Id {} | divide of {} and {}", MDC.get("X-Request-Id"), a, b);

        CalculationRequestDTO calculationRequestDTO = new CalculationRequestDTO("divide", a, b);
        return getCalculationResponseDTOResponseEntity(calculationRequestDTO);
    }

    private ResponseEntity<CalculationResponseDTO> getCalculationResponseDTOResponseEntity(CalculationRequestDTO calculationRequestDTO) {
        try {
            CalculationResponseDTO response = calculatorProducer.handleCalculationRequest(calculationRequestDTO);

            HttpStatus status;
            if (response.getResult() != null) {
                log.info("X-Request-Id {} | Calculation response (200 OK): {}", MDC.get("X-Request-Id"), response);
                status = HttpStatus.OK;
            } else if (response.getErrorMessage().compareTo("Divide by zero") == 0) {
                log.error("X-Request-Id {} | Error (400 Bad Request): {}", MDC.get("X-Request-Id"), response.getErrorMessage());
                status = HttpStatus.BAD_REQUEST;
            } else {
                log.error("X-Request-Id {} | Error (500 Internal Server Error): {}", MDC.get("X-Request-Id"), response.getErrorMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
            return ResponseEntity.status(status)
                    .header("X-Request-Id", MDC.get("X-Request-Id"))
                    .body(response);
        } catch (Exception e) {
            String errorMessage = "X-Request-Id " + MDC.get("X-Request-Id") + " | Unexpected error (500 Internal Server Error): " + e.getMessage();
            log.error(errorMessage, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("X-Request-Id", MDC.get("X-Request-Id"))
                    .body(new CalculationResponseDTO(errorMessage));
        }
    }

    private void setXRequestIdHeader(String requestId) {
        requestId = (requestId == null) ? UUID.randomUUID().toString() : requestId;
        MDC.put("X-Request-Id", requestId);
    }
}

package com.example.rest.controllers;

import com.example.rest.dtos.CalculationRequestDTO;
import com.example.rest.dtos.CalculationResponseDTO;
import com.example.rest.middleware.kafka.CalculatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/")
public class CalculatorController {

    private final CalculatorProducer calculatorProducer;

    @Autowired
    public CalculatorController(CalculatorProducer calculatorProducer) {
        this.calculatorProducer = calculatorProducer;
    }

    @GetMapping("/sum")
    public ResponseEntity<CalculationResponseDTO> sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        CalculationRequestDTO calculationRequestDTO = new CalculationRequestDTO("sum", a, b);
        return getCalculationResponseDTOResponseEntity(calculationRequestDTO);
    }

    @GetMapping("/subtract")
    public ResponseEntity<CalculationResponseDTO> subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        CalculationRequestDTO calculationRequestDTO = new CalculationRequestDTO("subtract", a, b);
        return getCalculationResponseDTOResponseEntity(calculationRequestDTO);
    }

    @GetMapping("/multiply")
    public ResponseEntity<CalculationResponseDTO> multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        CalculationRequestDTO calculationRequestDTO = new CalculationRequestDTO("multiply", a, b);
        return getCalculationResponseDTOResponseEntity(calculationRequestDTO);
    }

    @GetMapping("/divide")
    public ResponseEntity<CalculationResponseDTO> divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        CalculationRequestDTO calculationRequestDTO = new CalculationRequestDTO("divide", a, b);
        return getCalculationResponseDTOResponseEntity(calculationRequestDTO);
    }

    private ResponseEntity<CalculationResponseDTO> getCalculationResponseDTOResponseEntity(CalculationRequestDTO calculationRequestDTO) {
        try {
            CalculationResponseDTO response = calculatorProducer.handleCalculationRequest(calculationRequestDTO);

            HttpStatus status;
            if (response.getResult() != null) {
                status = HttpStatus.OK;
            } else if (response.getErrorMessage().compareTo("Divide by zero") == 0) {
                status = HttpStatus.BAD_REQUEST;
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(
                    new CalculationResponseDTO("Unexpected error: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}

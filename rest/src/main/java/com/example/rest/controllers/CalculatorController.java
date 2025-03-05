package com.example.rest.controllers;

import com.example.rest.dtos.CalculationRequestDTO;
import com.example.rest.dtos.CalculationResponseDTO;
import com.example.rest.middleware.kafka.CalculatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CalculationResponseDTO sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        CalculationRequestDTO calculationRequestDTO = new CalculationRequestDTO("sum", a, b);
        try {
            return calculatorProducer.handleCalculationRequest(calculationRequestDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return new CalculationResponseDTO("Unexpected error: " + e.getMessage());
        }
    }

    @GetMapping("/subtract")
    public CalculationResponseDTO subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        CalculationRequestDTO calculationRequestDTO = new CalculationRequestDTO("subtract", a, b);
        try {
            return calculatorProducer.handleCalculationRequest(calculationRequestDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return new CalculationResponseDTO("Unexpected error: " + e.getMessage());
        }
    }

    @GetMapping("/multiply")
    public CalculationResponseDTO multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        CalculationRequestDTO calculationRequestDTO = new CalculationRequestDTO("multiply", a, b);
        try {
            return calculatorProducer.handleCalculationRequest(calculationRequestDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return new CalculationResponseDTO("Unexpected error: " + e.getMessage());
        }
    }

    @GetMapping("/divide")
    public CalculationResponseDTO divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        CalculationRequestDTO calculationRequestDTO = new CalculationRequestDTO("divide", a, b);
        try {
            return calculatorProducer.handleCalculationRequest(calculationRequestDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return new CalculationResponseDTO("Unexpected error: " + e.getMessage());
        }
    }
}

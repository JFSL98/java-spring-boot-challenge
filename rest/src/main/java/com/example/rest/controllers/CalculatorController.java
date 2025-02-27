package com.example.rest.controllers;

import com.example.rest.dtos.CalculationRequestDTO;
import com.example.rest.dtos.CalculationResultDTO;
import com.example.rest.middleware.kafka.CalculatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class CalculatorController {
    @Autowired
    private CalculatorProducer calculatorProducer;

    @GetMapping("/sum")
    public CalculationResultDTO sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        CalculationRequestDTO calculationRequestDTO = new CalculationRequestDTO(UUID.randomUUID().toString(), "sum", a, b);
        calculatorProducer.sendCalculationRequest(calculationRequestDTO.getCorrelationId(), calculationRequestDTO);
        return new CalculationResultDTO(a.add(b));
    }

    @GetMapping("/subtract")
    public CalculationResultDTO subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return new CalculationResultDTO(a.subtract(b));
    }

    @GetMapping("/multiply")
    public CalculationResultDTO multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return new CalculationResultDTO(a.multiply(b));
    }

    @GetMapping("/divide")
    public CalculationResultDTO divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        BigDecimal result;
        try {
            result = a.divide(b, 10, RoundingMode.HALF_UP).stripTrailingZeros();
        } catch (ArithmeticException e) {
            result = null;
        }
        return new CalculationResultDTO(result);
    }
}

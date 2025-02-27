package com.example.rest.controllers;

import com.example.rest.dtos.CalculationResultDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("/")
public class CalculatorController {
    @GetMapping("/sum")
    public CalculationResultDTO sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
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
        CalculationResultDTO result = new CalculationResultDTO(null);
        try {
            result.setResult(a.divide(b, 10, RoundingMode.HALF_UP).stripTrailingZeros());
        } catch (ArithmeticException e) {
            result.setResult(null);
        }
        return result;
    }
}

package com.example.calculator.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculatorService {
    public BigDecimal calculate(String operator, BigDecimal a, BigDecimal b) {
        return switch (operator) {
            case "sum" -> sum(a, b);
            case "subtract" -> subtract(a, b);
            case "multiply" -> multiply(a, b);
            case "divide" -> divide(a, b);
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }

    private BigDecimal sum(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    private BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    private BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

    private BigDecimal divide(BigDecimal a, BigDecimal b) {
        try {
            return a.divide(b, 10, RoundingMode.HALF_UP).stripTrailingZeros();
        } catch (ArithmeticException e) {
            return null;
        }
    }
}

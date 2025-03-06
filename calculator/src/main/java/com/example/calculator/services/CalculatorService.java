package com.example.calculator.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculatorService {

    private static final Logger logger = LoggerFactory.getLogger(CalculatorService.class);

    public BigDecimal calculate(String operator, BigDecimal a, BigDecimal b) throws ArithmeticException, IllegalArgumentException {
        return switch (operator) {
            case "sum" -> sum(a, b);
            case "subtract" -> subtract(a, b);
            case "multiply" -> multiply(a, b);
            case "divide" -> divide(a, b);
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }

    private BigDecimal sum(BigDecimal a, BigDecimal b) {
        logger.info("Performing sum of {} and {}", a, b);
        return a.add(b).stripTrailingZeros();
    }

    private BigDecimal subtract(BigDecimal a, BigDecimal b) {
        logger.info("Performing subtraction of {} and {}", a, b);
        return a.subtract(b).stripTrailingZeros();
    }

    private BigDecimal multiply(BigDecimal a, BigDecimal b) {
        logger.info("Performing multiplication of {} and {}", a, b);
        return a.multiply(b).stripTrailingZeros();
    }

    private BigDecimal divide(BigDecimal a, BigDecimal b) throws ArithmeticException {
        logger.info("Performing division of {} and {}", a, b);
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Divide by zero");
        }
        return a.divide(b, 10, RoundingMode.HALF_UP).stripTrailingZeros();
    }
}

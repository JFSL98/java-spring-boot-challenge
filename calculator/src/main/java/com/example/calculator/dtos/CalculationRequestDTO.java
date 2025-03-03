package com.example.calculator.dtos;

import org.springframework.boot.jackson.JsonComponent;

import java.math.BigDecimal;

@JsonComponent
public class CalculationRequestDTO {
    private String operator;
    private BigDecimal a, b;

    public CalculationRequestDTO() {
    }

    public CalculationRequestDTO(String operator, BigDecimal a, BigDecimal b) {
        this.operator = operator;
        this.a = a;
        this.b = b;
    }

    public BigDecimal getA() {
        return a;
    }

    public BigDecimal getB() {
        return b;
    }

    public String getOperator() {
        return operator;
    }
}

package com.example.calculator.dtos;

import org.springframework.boot.jackson.JsonComponent;

import java.math.BigDecimal;

@JsonComponent
public class CalculationRequestDTO {
    private String correlationId, operator;
    private BigDecimal a, b;

    public CalculationRequestDTO() {
    }

    public CalculationRequestDTO(String correlationId, String operator, BigDecimal a, BigDecimal b) {
        this.correlationId = correlationId;
        this.operator = operator;
        this.a = a;
        this.b = b;
    }

    public String getCorrelationId() {
        return correlationId;
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

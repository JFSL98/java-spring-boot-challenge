package com.example.rest.dtos;

import java.math.BigDecimal;

public class CalculationRequestDTO {
    private final String correlationId, operator;
    private final BigDecimal a, b;

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

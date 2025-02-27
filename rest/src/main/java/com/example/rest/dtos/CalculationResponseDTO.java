package com.example.rest.dtos;

import java.math.BigDecimal;

public class CalculationResponseDTO {
    private final String correlationId;
    private final BigDecimal result;

    public CalculationResponseDTO(String correlationId, BigDecimal result) {
        this.correlationId = correlationId;
        this.result = result;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public BigDecimal getResult() {
        return result;
    }
}

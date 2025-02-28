package com.example.calculator.dtos;

import org.springframework.boot.jackson.JsonComponent;

import java.math.BigDecimal;

@JsonComponent
public class CalculationResponseDTO {
    private String correlationId;
    private BigDecimal result;

    public CalculationResponseDTO() {
    }

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

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }
}

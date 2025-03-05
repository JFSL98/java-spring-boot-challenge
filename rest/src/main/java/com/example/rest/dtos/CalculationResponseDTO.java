package com.example.rest.dtos;

import org.springframework.boot.jackson.JsonComponent;

import java.math.BigDecimal;

@JsonComponent
public class CalculationResponseDTO {
    private BigDecimal result;
    private String errorMessage;

    public CalculationResponseDTO() {
    }

    public CalculationResponseDTO(BigDecimal result) {
        this.result = result;
        this.errorMessage = null;
    }

    public CalculationResponseDTO(String errorMessage) {
        this.errorMessage = errorMessage;
        this.result = null;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

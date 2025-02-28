package com.example.rest.dtos;

import org.springframework.boot.jackson.JsonComponent;

import java.math.BigDecimal;

@JsonComponent
public class CalculationResultDTO {
    private BigDecimal result;

    public CalculationResultDTO() {}

    public CalculationResultDTO(BigDecimal result) {
        this.result = result;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }
}

package com.example.rest.dtos;

import java.math.BigDecimal;

public class CalculationResultDTO {
    private final BigDecimal result;

    public CalculationResultDTO(BigDecimal result) {
        this.result = result;
    }

    public BigDecimal getResult() {
        return result;
    }
}

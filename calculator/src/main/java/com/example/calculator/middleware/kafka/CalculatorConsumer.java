package com.example.calculator.middleware.kafka;

import com.example.calculator.dtos.CalculationRequestDTO;
import com.example.calculator.dtos.CalculationResponseDTO;
import com.example.calculator.services.CalculatorService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CalculatorConsumer {
    private final CalculatorService calculatorService;

    @Autowired
    public CalculatorConsumer(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @KafkaListener(id = "calc", topics = "calculation-requests")
    @SendTo
    public CalculationResponseDTO listen(ConsumerRecord<String, CalculationRequestDTO> record) {
        try {
            System.out.println("Received Message: " + record.value().getOperator() + ", " + record.value().getA() + ", " + record.value().getB());
            CalculationRequestDTO request = record.value();
            BigDecimal result = calculatorService.calculate(request.getOperator(), request.getA(), request.getB());
            return new CalculationResponseDTO(result);
        } catch (Exception e) {
            return new CalculationResponseDTO(e.getMessage());
        }
    }
}

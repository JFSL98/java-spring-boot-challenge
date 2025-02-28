package com.example.calculator.middleware.kafka;

import com.example.calculator.dtos.CalculationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CalculatorProducer {
    private final KafkaTemplate<String, CalculationResponseDTO> kafkaTemplate;

    @Autowired
    public CalculatorProducer(KafkaTemplate<String, CalculationResponseDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCalculationResult(String correlationId, CalculationResponseDTO response) {
        System.out.println("Sending CalculationResponseDTO: " + correlationId);
        kafkaTemplate.send("calculation-results", correlationId, response);
    }
}

package com.example.rest.middleware.kafka;

import com.example.rest.dtos.CalculationRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CalculatorProducer {
    private final KafkaTemplate<String, CalculationRequestDTO> kafkaTemplate;

    @Autowired
    public CalculatorProducer(KafkaTemplate<String, CalculationRequestDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCalculationRequest(String correlationId, CalculationRequestDTO request) {
        System.out.println("Sending CalculationRequestDTO: " + correlationId);
        kafkaTemplate.send("calculation-requests", correlationId, request);
    }
}

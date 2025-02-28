package com.example.calculator.middleware.kafka;

import com.example.calculator.dtos.CalculationRequestDTO;
import com.example.calculator.dtos.CalculationResponseDTO;
import com.example.calculator.services.CalculatorService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CalculatorConsumer {
    private final CalculatorProducer producer;
    private final CalculatorService calculatorService;

    @Autowired
    public CalculatorConsumer(CalculatorProducer producer, CalculatorService calculatorService) {
        this.producer = producer;
        this.calculatorService = calculatorService;
    }

    @KafkaListener(topics = "calculation-requests", groupId = "group")
    public void listen(ConsumerRecord<String, CalculationRequestDTO> record) {
        System.out.println("Received Message: " + record.value());
        CalculationRequestDTO request = record.value();
        BigDecimal result = calculatorService.calculate(request.getOperator(), request.getA(), request.getB());
        CalculationResponseDTO response = new CalculationResponseDTO(request.getCorrelationId(), result);
        producer.sendCalculationResult(request.getCorrelationId(), response);
    }
}

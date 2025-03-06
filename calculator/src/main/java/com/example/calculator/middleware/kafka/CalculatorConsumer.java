package com.example.calculator.middleware.kafka;

import com.example.calculator.dtos.CalculationRequestDTO;
import com.example.calculator.dtos.CalculationResponseDTO;
import com.example.calculator.services.CalculatorService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

@Component
public class CalculatorConsumer {

    private static final Logger log = LoggerFactory.getLogger(CalculatorConsumer.class);

    private final CalculatorService calculatorService;

    @Autowired
    public CalculatorConsumer(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @KafkaListener(id = "calc", topics = "calculation-requests")
    @SendTo
    public CalculationResponseDTO listen(ConsumerRecord<String, CalculationRequestDTO> record) {
        try {
            Header requestIdHeader = record.headers().lastHeader("X-Request-Id");
            String requestId = requestIdHeader != null ? new String(requestIdHeader.value(), StandardCharsets.UTF_8) : "invalid_request_id";
            MDC.put("X-Request-Id", requestId);
            log.info("X-Request-Id {} | Received request: {}", MDC.get("X-Request-Id"), record.value());
            CalculationRequestDTO request = record.value();
            BigDecimal result = calculatorService.calculate(request.getOperator(), request.getA(), request.getB());
            log.info("X-Request-Id {} | Replying with result: {}", MDC.get("X-Request-Id"), result);
            return new CalculationResponseDTO(result);
        } catch (Exception e) {
            log.error("X-Request-Id {} | {}", MDC.get("X-Request-Id"), e.getMessage(), e);
            return new CalculationResponseDTO(e.getMessage());
        }
    }
}

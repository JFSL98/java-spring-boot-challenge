package com.example.rest.middleware.kafka;

import com.example.rest.dtos.CalculationRequestDTO;
import com.example.rest.dtos.CalculationResponseDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
public class CalculatorProducer {

    private static final Logger log = LoggerFactory.getLogger(CalculatorProducer.class);

    private final ReplyingKafkaTemplate<String, CalculationRequestDTO, CalculationResponseDTO> replyingKafkaTemplate;

    @Autowired
    public CalculatorProducer(ReplyingKafkaTemplate<String, CalculationRequestDTO, CalculationResponseDTO> replyingKafkaTemplate) {
        this.replyingKafkaTemplate = replyingKafkaTemplate;
    }

    public CalculationResponseDTO handleCalculationRequest(CalculationRequestDTO request) throws ExecutionException, InterruptedException, TimeoutException {
        ProducerRecord<String, CalculationRequestDTO> record = new ProducerRecord<>("calculation-requests", request);
        log.info("X-Request-Id {} | Sending request to kafka topic: {}", MDC.get("X-Request-Id"), record.topic());
        log.debug("X-Request-Id {} | With data: {}", MDC.get("X-Request-Id"), record.value());
        RequestReplyFuture<String, CalculationRequestDTO, CalculationResponseDTO> replyFuture = replyingKafkaTemplate.sendAndReceive(record);
        SendResult<String, CalculationRequestDTO> sendResult = replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);
        ConsumerRecord<String, CalculationResponseDTO> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
        log.info("X-Request-Id {} | Received response from kafka topic: {}", MDC.get("X-Request-Id"), consumerRecord.topic());
        log.debug("X-Request-Id {} | With data: {}", MDC.get("X-Request-Id"), consumerRecord.value());
        return consumerRecord.value();
    }
}

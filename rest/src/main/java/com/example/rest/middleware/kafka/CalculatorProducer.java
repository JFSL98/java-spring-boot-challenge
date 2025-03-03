package com.example.rest.middleware.kafka;

import com.example.rest.dtos.CalculationRequestDTO;
import com.example.rest.dtos.CalculationResponseDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
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
    private final ReplyingKafkaTemplate<String, CalculationRequestDTO, CalculationResponseDTO> replyingKafkaTemplate;

    @Autowired
    public CalculatorProducer(ReplyingKafkaTemplate<String, CalculationRequestDTO, CalculationResponseDTO> replyingKafkaTemplate) {
        this.replyingKafkaTemplate = replyingKafkaTemplate;
    }

    public CalculationResponseDTO handleCalculationRequest(CalculationRequestDTO request) throws ExecutionException, InterruptedException, TimeoutException {
        ProducerRecord<String, CalculationRequestDTO> record = new ProducerRecord<>("calculation-requests", request);
        RequestReplyFuture<String, CalculationRequestDTO, CalculationResponseDTO> replyFuture = replyingKafkaTemplate.sendAndReceive(record);
        SendResult<String, CalculationRequestDTO> sendResult = replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);
        ConsumerRecord<String, CalculationResponseDTO> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
        System.out.println("Return result: " + consumerRecord.value().getResult());
        return consumerRecord.value();
    }
}

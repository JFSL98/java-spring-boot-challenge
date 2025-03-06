package com.example.calculator;

import com.example.rest.dtos.CalculationRequestDTO;
import com.example.rest.dtos.CalculationResponseDTO;
import com.example.rest.middleware.kafka.CalculatorProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculatorProducerTests {

    @Mock
    private ReplyingKafkaTemplate<String, CalculationRequestDTO, CalculationResponseDTO> replyingKafkaTemplate;

    @InjectMocks
    private CalculatorProducer calculatorProducer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleCalculationRequest() throws ExecutionException, InterruptedException, TimeoutException {
        // Prepare Data
        CalculationRequestDTO requestDTO = new CalculationRequestDTO("sum", new BigDecimal(1), new BigDecimal(2));
        CalculationResponseDTO responseDTO = new CalculationResponseDTO(new BigDecimal(3));

        ProducerRecord<String, CalculationRequestDTO> record = new ProducerRecord<>("calculation-requests", requestDTO);

        // Mock
        RequestReplyFuture<String, CalculationRequestDTO, CalculationResponseDTO> future = mock(RequestReplyFuture.class);
        when(replyingKafkaTemplate.sendAndReceive(record)).thenReturn(future);

        SendResult<String, CalculationRequestDTO> sendResult = mock(SendResult.class);
        when(future.getSendFuture()).thenReturn(CompletableFuture.completedFuture(sendResult));

        ConsumerRecord<String, CalculationResponseDTO> consumerRecord = new ConsumerRecord<>(
                "calculation-results", 0, 0, "key123", responseDTO
        );
        when(future.get(10, TimeUnit.SECONDS)).thenReturn(consumerRecord);

        // Act
        CalculationResponseDTO result = calculatorProducer.handleCalculationRequest(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal(3), result.getResult());
    }
}

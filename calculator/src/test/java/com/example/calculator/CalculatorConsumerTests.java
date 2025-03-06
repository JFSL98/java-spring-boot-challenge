package com.example.calculator;

import com.example.calculator.dtos.CalculationRequestDTO;
import com.example.calculator.dtos.CalculationResponseDTO;
import com.example.calculator.middleware.kafka.CalculatorConsumer;
import com.example.calculator.services.CalculatorService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class CalculatorConsumerTests {

    @Mock
    private CalculatorService calculatorService;

    @InjectMocks
    private CalculatorConsumer calculatorConsumer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListen() {
        // Prepare data
        CalculationRequestDTO requestDTO = new CalculationRequestDTO("sum", new BigDecimal(1), new BigDecimal(2));
        ConsumerRecord<String, CalculationRequestDTO> record =
                new ConsumerRecord<>(
                        "calculation-requests",
                        0,
                        0,
                        "key123",
                        requestDTO);

        // Mock
        when(calculatorService.calculate("sum", new BigDecimal(1), new BigDecimal(2)))
                .thenReturn(new BigDecimal(3));

        // Act
        CalculationResponseDTO responseDTO = calculatorConsumer.listen(record);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(new BigDecimal(3), responseDTO.getResult());
    }
}

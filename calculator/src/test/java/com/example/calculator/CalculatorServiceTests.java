package com.example.calculator;

import com.example.calculator.services.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorServiceTests {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }

    // SUM
    @Test
    public void testSumPositive() {
        BigDecimal result = calculatorService.calculate("sum", new BigDecimal(1), new BigDecimal(2));
        assertEquals(new BigDecimal(3), result);
    }

    @Test
    public void testSumNegative() {
        BigDecimal result = calculatorService.calculate("sum", new BigDecimal(-1), new BigDecimal(-2));
        assertEquals(new BigDecimal(-3), result);
    }

    @Test
    public void testSumDecimals() {
        BigDecimal result = calculatorService.calculate("sum", new BigDecimal("1.1"), new BigDecimal("2.2"));
        assertEquals(new BigDecimal("3.3"), result);
    }

    @Test
    public void testSumBigNumbers() {
        BigDecimal result = calculatorService.calculate(
                "sum",
                new BigDecimal("1000000000000000000"),
                new BigDecimal("2000000000000000000"));
        assertEquals(new BigDecimal("3000000000000000000").stripTrailingZeros().toPlainString(), result.stripTrailingZeros().toPlainString());
    }

    @Test
    public void testSumZeros() {
        BigDecimal result = calculatorService.calculate(
                "sum",
                new BigDecimal(0),
                new BigDecimal(0));
        assertEquals(new BigDecimal(0), result);
    }

    // SUBTRACT
    @Test
    public void testSubtractPositive() {
        BigDecimal result = calculatorService.calculate("subtract", new BigDecimal(3), new BigDecimal(2));
        assertEquals(new BigDecimal(1), result);
    }

    @Test
    public void testSubtractNegative() {
        BigDecimal result = calculatorService.calculate("subtract", new BigDecimal(-1), new BigDecimal(-2));
        assertEquals(new BigDecimal(1), result);
    }

    @Test
    public void testSubtractDecimals() {
        BigDecimal result = calculatorService.calculate("subtract", new BigDecimal("4.4"), new BigDecimal("1.1"));
        assertEquals(new BigDecimal("3.3"), result);
    }

    @Test
    public void testSubtractBigNumbers() {
        BigDecimal result = calculatorService.calculate("subtract", new BigDecimal("3000000000000000000"), new BigDecimal("2000000000000000000"));
        assertEquals(new BigDecimal("1000000000000000000").stripTrailingZeros().toPlainString(), result.stripTrailingZeros().toPlainString());
    }

    @Test
    public void testSubtractZeros() {
        BigDecimal result = calculatorService.calculate("subtract", new BigDecimal(0), new BigDecimal(0));
        assertEquals(new BigDecimal(0), result);
    }

    // MULTIPLY
    @Test
    public void testMultiplyPositive() {
        BigDecimal result = calculatorService.calculate("multiply", new BigDecimal(3), new BigDecimal(2));
        assertEquals(new BigDecimal(6), result);
    }

    @Test
    public void testMultiplyNegative() {
        BigDecimal result = calculatorService.calculate("multiply", new BigDecimal(-3), new BigDecimal(-2));
        assertEquals(new BigDecimal(6), result);
    }

    @Test
    public void testMultiplyPositiveAndNegative() {
        BigDecimal result = calculatorService.calculate("multiply", new BigDecimal(3), new BigDecimal(-2));
        assertEquals(new BigDecimal(-6), result);
    }

    @Test
    public void testMultiplyDecimals() {
        BigDecimal result = calculatorService.calculate("multiply", new BigDecimal("1.1"), new BigDecimal("3.0"));
        assertEquals(new BigDecimal("3.3"), result);
    }

    @Test
    public void testMultiplyBigNumbers() {
        BigDecimal result = calculatorService.calculate("multiply", new BigDecimal("1000000000"), new BigDecimal("2000000000"));
        assertEquals(new BigDecimal("2000000000000000000").stripTrailingZeros().toPlainString(), result.stripTrailingZeros().toPlainString());
    }

    @Test
    public void testMultiplyZeros() {
        BigDecimal result = calculatorService.calculate("multiply", new BigDecimal(0), new BigDecimal(5));
        assertEquals(new BigDecimal(0), result);
    }

    @Test
    public void testMultiplyByOne() {
        BigDecimal result = calculatorService.calculate("multiply", new BigDecimal("12345.678"), new BigDecimal(1));
        assertEquals(new BigDecimal("12345.678"), result);
    }

    // DIVIDE
    @Test
    public void testDividePositive() {
        BigDecimal result = calculatorService.calculate("divide", new BigDecimal(6), new BigDecimal(2));
        assertEquals(new BigDecimal(3), result);
    }

    @Test
    public void testDivideNegative() {
        BigDecimal result = calculatorService.calculate("divide", new BigDecimal(-6), new BigDecimal(-2));
        assertEquals(new BigDecimal(3), result);
    }

    @Test
    public void testDividePositiveAndNegative() {
        BigDecimal result = calculatorService.calculate("divide", new BigDecimal(6), new BigDecimal(-2));
        assertEquals(new BigDecimal(-3), result);
    }

    @Test
    public void testDivideDecimals() {
        BigDecimal result = calculatorService.calculate("divide", new BigDecimal("4.4"), new BigDecimal("2.2"));
        assertEquals(new BigDecimal("2"), result.stripTrailingZeros());
    }

    @Test
    public void testDivideBigNumbers() {
        BigDecimal result = calculatorService.calculate("divide", new BigDecimal("1000000000"), new BigDecimal("2"));
        assertEquals(new BigDecimal("500000000").stripTrailingZeros().toPlainString(), result.stripTrailingZeros().toPlainString());
    }

    @Test
    public void testDivideByOne() {
        BigDecimal result = calculatorService.calculate("divide", new BigDecimal("12345.678"), new BigDecimal(1));
        assertEquals(new BigDecimal("12345.678"), result);
    }

    @Test
    public void testDivideByZero() {
        assertThrows(ArithmeticException.class, () ->
                calculatorService.calculate("divide", new BigDecimal(5), new BigDecimal(0))
        );
    }
}

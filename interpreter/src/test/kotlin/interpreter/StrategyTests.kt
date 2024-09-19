package interpreter

import interpreter.strategies.AdditionStrategy
import interpreter.strategies.DivisionStrategy
import interpreter.strategies.MultiplicationStrategy
import interpreter.strategies.SubtractionStrategy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class StrategyTests {
    private val additionStrategy = AdditionStrategy()
    private val subtractionStrategy = SubtractionStrategy()
    private val multiplicationStrategy = MultiplicationStrategy()
    private val divisionStrategy = DivisionStrategy()

    @Test
    fun `test addition of positive numbers`() {
        val result = additionStrategy.execute(5.0, 3.0)
        assertEquals(8.0, result)
    }

    @Test
    fun `test addition of negative numbers`() {
        val result = additionStrategy.execute(-5.0, -3.0)
        assertEquals(-8.0, result)
    }

    @Test
    fun `test addition of number and zero`() {
        val result = additionStrategy.execute(5.0, 0.0)
        assertEquals(5.0, result)
    }

    @Test
    fun `test addition of large numbers`() {
        val result = additionStrategy.execute(1e10, 1e10)
        assertEquals(2e10, result)
    }

    @Test
    fun `test subtraction of positive numbers`() {
        val result = subtractionStrategy.execute(5.0, 3.0)
        assertEquals(2.0, result)
    }

    @Test
    fun `test subtraction of negative numbers`() {
        val result = subtractionStrategy.execute(-5.0, -3.0)
        assertEquals(-2.0, result)
    }

    @Test
    fun `test subtraction with zero`() {
        val result = subtractionStrategy.execute(5.0, 0.0)
        assertEquals(5.0, result)
    }

    @Test
    fun `test subtraction resulting in negative number`() {
        val result = subtractionStrategy.execute(3.0, 5.0)
        assertEquals(-2.0, result)
    }

    @Test
    fun `test multiplication of positive numbers`() {
        val result = multiplicationStrategy.execute(5.0, 3.0)
        assertEquals(15.0, result)
    }

    @Test
    fun `test multiplication of negative numbers`() {
        val result = multiplicationStrategy.execute(-5.0, -3.0)
        assertEquals(15.0, result)
    }

    @Test
    fun `test multiplication by zero`() {
        val result = multiplicationStrategy.execute(5.0, 0.0)
        assertEquals(0.0, result)
    }

    @Test
    fun `test multiplication with large numbers`() {
        val result = multiplicationStrategy.execute(1e5, 1e5)
        assertEquals(1e10, result)
    }

    @Test
    fun `test division of positive numbers`() {
        val result = divisionStrategy.execute(6.0, 3.0)
        assertEquals(2.0, result)
    }

    @Test
    fun `test division of negative numbers`() {
        val result = divisionStrategy.execute(-6.0, -3.0)
        assertEquals(2.0, result)
    }

    @Test
    fun `test division by one`() {
        val result = divisionStrategy.execute(5.0, 1.0)
        assertEquals(5.0, result)
    }

    @Test
    fun `test division by zero throws exception`() {
        assertThrows<ArithmeticException> {
            divisionStrategy.execute(5.0, 0.0)
        }
    }
}

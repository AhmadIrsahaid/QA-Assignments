package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.Calculator;

@DisplayName("Calculator Test Class")
class Calculatortest {

    Calculator calc = new Calculator();
    
    @ParameterizedTest(name = "Add {0} + {1} = {2}")
    @CsvSource({
        "4, 5, 9",
        "10, 20, 30",
        "-5, 5, 0",
        "0, 0, 0"
    })
    @DisplayName("Parameterized test for add function")
    void testAdd(int a, int b, int expected) {
        assertEquals(expected, calc.add(a, b));
    }

    @Test    
    @DisplayName("Test add function 2 (using assertFalse on wrong expectation)")
    void testAdd2() {
        int res = calc.add(4, 5);
        assertFalse(res == 10, "Expected result is NOT 10"); // 4 + 5 = 9, so 10 is wrong
    }

    @Test
    @DisplayName("Test divide function")
    void testDivide() {
        int res = calc.divide(8, 2);
        assertEquals(4, res);
    }

    @Test
    @DisplayName("Test divide function 2 (assertFalse used to guard against wrong expectation)")
    void testDivide2() {
        int res = calc.divide(8, 2);
        assertFalse(res == 5, "Expected result is NOT 5"); // 8 / 2 = 4
    }

    @Test
    @DisplayName("Test factorial function")
    void testFactorial() {
        int res = calc.factorial(5);
        assertEquals(120, res);
    }

    @Test
    @DisplayName("Test factorial function 2 (assertFalse for wrong expectation)")
    void testFactorial2() {
        int res = calc.factorial(5);
        assertFalse(res == 100, "Expected result is NOT 100"); // factorial(5) = 120
    }

    @Test
    @DisplayName("Test factorial completes within 1 second")
    void testFactorialTimeout() {
        assertTimeout(Duration.ofSeconds(1), () -> {
            calc.factorial(10);
        }, "Factorial calculation took too long!");
    }

    @Test
    @DisplayName("Test divide with invalid input (division by zero)")
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> calc.divide(10, 0));
    }

    @Test
    @DisplayName("Test multiple assertions for add function")
    void testAddMultipleAssertions() {
        int result = calc.add(3, 7);
        assertAll("Multiple assertions for add",
            () -> assertEquals(10, result),
            () -> assertTrue(result > 0),
            () -> assertNotEquals(0, result)
        );
    }

    @Test
    @DisplayName("Failing add example: assertFalse to detect incorrect expected value")
    void testFailingAddExample() {
        int result = calc.add(6, 6);
        assertFalse(result == 15, "Expected result is NOT 15"); // Correct result = 12
    }
}

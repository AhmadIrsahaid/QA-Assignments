package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Disabled;
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
    @DisplayName("Test add function 2")
    void testAdd2() {
        int res = calc.add(4,5);
        assertEquals(10, res);
    }

    @Test
    @DisplayName("Test divide function")
    void testDivide() {
        int res = calc.divide(8,2);
        assertEquals(4, res);
    }

    @Test
    @DisplayName("Test divide function 2")
    void testDivide2() {
        int res = calc.divide(8,2);
        assertEquals(5, res);
    }

    @Test
    @DisplayName("Test factorial function")
    void testFactorial() {
        int res = calc.factorial(5);
        assertEquals(120, res);
    }

    @Test
    @Disabled("This test is expected to fail; correct expected = 120") 
    @DisplayName("Test factorial function 2")
    void testFactorial2() {
        int res = calc.factorial(5);
        assertEquals(100, res);
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
    @Disabled("Intentionally fails: expected is 15 but actual is 12. Fix by changing expected to 12.")
    @DisplayName("Intentionally failing test for add")
    void testFailingAddExample() {
        int result = calc.add(6, 6);
        assertEquals(15, result);
    }
}

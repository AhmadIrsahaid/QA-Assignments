package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.UserService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Execution(value = ExecutionMode.CONCURRENT)
@DisplayName("UserService Test Class")
class UserServiceTest {

    UserService test = new UserService();

    @Test
    @Order(1)
    @DisplayName("Valid test email function")
    void testIsValidEmail() {
        String email = "ahmad@gmail.com";
        assertTrue(test.isValidEmail(email), "The email is valid");
    }

    @Test
    @Order(3)
    @DisplayName("Invalid test email function")
    void testInValidEmail() {
        String email = "ahmadgmailcom";
        assertFalse(test.isValidEmail(email), "The email is invalid");
    }

    @Test
    @Order(2)
    @DisplayName("Valid test authenticate")
    void testAuthenticateIsValid() {
        String username = "admin";
        String password = "1234";
        assertTrue(test.authenticate(username, password), "The authentication should be valid");
    }

    @Test
    @Order(4)
    @DisplayName("Invalid test authenticate")
    void testAuthenticateInValid() {
        String username = "Ali";
        String password = "12224";
        assertFalse(test.authenticate(username, password), "The authentication should be invalid");
    }

    @Test
    @Order(5)
    @DisplayName("Test authentication completes within 1 second")
    void testAuthenticationTimeout() {
        assertTimeout(Duration.ofSeconds(1), () -> {
            test.authenticate("admin", "1234");
        }, "Authentication took too long");
    }

    @ParameterizedTest
    @CsvSource({
        "user@example.com, true",
        "invalid-email, false",
        "test@domain.org, true",
        "noatsymbol.com, false"
    })
    @DisplayName("Parameterized test for email validation")
    void testIsValidEmailParameterized(String email, boolean expected) {
        assertEquals(expected, test.isValidEmail(email));
    }

    @Test
    @DisplayName("Multiple assertions for valid login")
    void testAuthenticateMultipleAssertions() {
        String username = "admin";
        String password = "1234";
        boolean result = test.authenticate(username, password);
        assertAll("Authentication checks",
            () -> assertTrue(result),
            () -> assertNotNull(username),
            () -> assertEquals("admin", username)
        );
    }

    @Test
    @Disabled("Intentionally fails: incorrect expected result. Fix by changing expected to true.")
    @DisplayName("Intentionally failing email test")
    void testFailingEmailCheck() {
        String email = "admin@example.com";
        assertFalse(test.isValidEmail(email)); 
    }
}

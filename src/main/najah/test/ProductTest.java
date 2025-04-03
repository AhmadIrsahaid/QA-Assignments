package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

//import org.junit.jupiter.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.Product;

@DisplayName("Product Test Class")
class ProductTest {

    Product product1 = new Product("Mobile", 1000);
    Product product2 = new Product("PC", 100);
    Product product3 = new Product("HeadPhone", 150);
    Product product4 = new Product("Smart Watch", 270);

    @BeforeAll
    static void beforeAll() {
        System.out.println("ProductTest: All tests are about to start...");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("ProductTest: All tests are done.");
    }

    @BeforeEach
    void setup() {
        Product setUpProduct = new Product("Camera", 500.0);
        System.out.println("ProductTest: Setup complete");
    }

    @AfterEach
    void tearDown() {
        System.out.println("ProductTest: Test finished");
    }

    @Test
    @DisplayName("test valid Discounts function")
    void testApplyDiscount() {
        assertAll("valid Discounts",
                () -> assertThrows(IllegalArgumentException.class, () -> product1.applyDiscount(35)),
                () -> assertThrows(IllegalArgumentException.class, () -> product2.applyDiscount(20)),
                () -> assertThrows(IllegalArgumentException.class, () -> product3.applyDiscount(0)),
                () -> assertThrows(IllegalArgumentException.class, () -> product2.applyDiscount(50)));
    }

    @Test
    @DisplayName("Test getFinalPrice")
    void testGetFinalPrice() {
        product1.applyDiscount(12);
        assertEquals(880.0, product1.getFinalPrice());
    }

    @Test
    @DisplayName("Test getName")
    void testGetName() {
        assertEquals("PC", product2.getName());
    }

    @Test
    @DisplayName("Test getPrice")
    void testGetPrice() {
        assertEquals(100, product2.getPrice());
    }

    @Test
    @DisplayName("Test getDiscount")
    void testGetDiscount() {
        product1.applyDiscount(20.0);
        assertEquals(20.0, product1.getDiscount());
    }

    @Test
    @DisplayName("Test getDiscount completes within 500ms")
    void testTimeOutGetDiscount() {
        assertTimeout(Duration.ofMillis(500), () -> {
            product1.applyDiscount(20.0);
            assertEquals(20.0, product1.getDiscount());
        }, "Applying discount took too long!");
    }

    @Test
    @DisplayName("Test invalid input - negative discount")
    void testInvalidDiscount() {
        assertThrows(IllegalArgumentException.class, () -> product4.applyDiscount(-10));
    }

    @ParameterizedTest
    @CsvSource({
        "Mobile, 1000.0",
        "PC, 100.0",
        "HeadPhone, 150.0",
        "Smart Watch, 270.0"
    })
    @DisplayName("Parameterized test for product name and price")
    void testProductNameAndPrice(String name, double price) {
        Product p = new Product(name, price);
        assertAll("Product details",
            () -> assertEquals(name, p.getName()),
            () -> assertEquals(price, p.getPrice())
        );
    }
}

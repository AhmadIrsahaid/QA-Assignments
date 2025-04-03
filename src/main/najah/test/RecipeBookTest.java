package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;

@DisplayName("RecipeBook Test Class")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecipeBookTest {

    RecipeBook book;
    Recipe recipe1;
    Recipe recipe2;

    @BeforeAll
    static void beforeAll() {
        System.out.println("=== Starting RecipeBook Tests ===");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("=== Finished RecipeBook Tests ===");
    }

    @BeforeEach
    void setup() {
        book = new RecipeBook();
        recipe1 = new Recipe();
        recipe1.setName("Latte");

        recipe2 = new Recipe();
        recipe2.setName("Espresso");

        System.out.println("Setup complete.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test finished.");
    }

    @Test
    @Order(1)
    @DisplayName("Test adding a valid recipe")
    void testAddValidRecipe() {
        assertTrue(book.addRecipe(recipe1));
    }

    @Test
    @Order(2)
    @DisplayName("Test adding a duplicate recipe (invalid)")
    void testAddDuplicateRecipe() {
        book.addRecipe(recipe1);
        assertFalse(book.addRecipe(recipe1));
    }

    @Test
    @Order(3)
    @DisplayName("Test deleting an existing recipe")
    void testDeleteExistingRecipe() {
        book.addRecipe(recipe2);
        String result = book.deleteRecipe(0);
        assertEquals("Espresso", result);
    }

    @Test
    @Order(4)
    @DisplayName("Test deleting a non-existing recipe (invalid)")
    void testDeleteNonExistingRecipe() {
        String result = book.deleteRecipe(2); // not added yet
        assertNull(result);
    }

    @Test
    @Order(5)
    @DisplayName("Test editing an existing recipe")
    void testEditRecipe() {
        book.addRecipe(recipe1);
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Cappuccino");
        String result = book.editRecipe(0, newRecipe);
        assertEquals("Latte", result);
    }

    @Test
    @Order(6)
    @DisplayName("Test edit invalid recipe slot")
    void testEditInvalidRecipe() {
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Mocha");
        String result = book.editRecipe(2, newRecipe);
        assertNull(result);
    }

    @Test
    @Order(7)
    @DisplayName("Test getRecipes returns correct size and content")
    void testGetRecipesMultipleAssertions() {
        book.addRecipe(recipe1);
        Recipe[] recipes = book.getRecipes();
        assertAll("Recipe array checks",
            () -> assertEquals(4, recipes.length),
            () -> assertEquals("Latte", recipes[0].getName()),
            () -> assertNull(recipes[1])
        );
    }

    @Test
    @Order(8)
    @DisplayName("Test add recipe completes within 500ms")
    void testAddRecipeTimeout() {
        assertTimeout(Duration.ofMillis(500), () -> {
            book.addRecipe(recipe2);
        }, "Adding recipe took too long!");
    }

    @ParameterizedTest
    @Order(9)
    @CsvSource({
        "Latte, true",
        "Espresso, true",
        "Latte, false"  
    })
    @DisplayName("Parameterized test for recipe addition")
    void testAddParameterized(String name, boolean expected) {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        boolean result = book.addRecipe(recipe);
        assertEquals(expected, result);
    }

    @Test
    @Order(10)
    @Disabled("Fails intentionally: assumes deleted recipe should be null. Fix by checking actual implementation.")
    @DisplayName("Intentional failing test for deleted recipe content")
    void testDeletedRecipeFails() {
        book.addRecipe(recipe1);
        book.deleteRecipe(0);
        assertNull(book.getRecipes()[0]); // ← Actually replaced with new empty Recipe
    }
}

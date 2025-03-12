package com.sander.tolkiens_cookbook.repository;

import com.sander.tolkiens_cookbook.exception.ResourceNotFoundException;
import com.sander.tolkiens_cookbook.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link RecipeDAO}.
 */
class RecipeDAOTest {

    @InjectMocks
    private RecipeDAO recipeDAO;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeIngredientRepository recipeIngredientRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    /**
     * Test to find all recipes in the database.
     */
    @Test
    void testFindAllRecipes() {
        Ingredient tomato = new Ingredient("Tomato", Category.VEGETABLE);
        Recipe recipe = new Recipe("Recipe 1", 2);
        RecipeIngredient ri = new RecipeIngredient(recipe, tomato, "100g");
        recipe.setIngredients(List.of(ri));

        when(recipeRepository.findAll()).thenReturn(List.of(recipe));

        List<Recipe> result = recipeDAO.findAll();

        assertEquals(1, result.size());
        assertEquals("Recipe 1", result.get(0).getName());
        assertEquals(1, result.get(0).getIngredients().size());
        verify(recipeRepository, times(1)).findAll();
    }

    /**
     * Test to find a specific recipe in the database by id.
     */
    @Test
    void testFindRecipeById_Found() {
        Ingredient tomato = new Ingredient("Tomato", Category.VEGETABLE);
        Recipe recipe = new Recipe("Test Recipe", 4);
        RecipeIngredient ri = new RecipeIngredient(recipe, tomato, "100g");
        recipe.setIngredients(List.of(ri));

        when(recipeRepository.findById(1)).thenReturn(Optional.of(recipe));

        Optional<Recipe> result = recipeDAO.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Test Recipe", result.get().getName());
        assertEquals(1, result.get().getIngredients().size());
    }

    /**
     * Test to find a specific recipe in the database by id that does not exist.
     */
    @Test
    void testFindRecipeById_NotFound() {
        when(recipeRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Recipe> result = recipeDAO.findById(1);

        assertFalse(result.isPresent());
    }

    /**
     * Test to save a new recipe in the database.
     */
    @Test
    void testSaveRecipe() {
        Ingredient tomato = new Ingredient("Tomato", Category.VEGETABLE);
        tomato.setId(1);
        Recipe recipe = new Recipe("Pasta", 2);
        RecipeIngredient ri = new RecipeIngredient();
        ri.setIngredient(tomato);
        ri.setQuantity("100g");
        recipe.setIngredients(List.of(ri));

        when(ingredientRepository.findById(1)).thenReturn(Optional.of(tomato));
        when(recipeRepository.save(any())).thenAnswer(i -> {
            Recipe r = i.getArgument(0);
            r.setId(10);
            return r;
        });

        Recipe savedRecipe = recipeDAO.save(recipe);

        assertNotNull(savedRecipe);
        assertEquals(10, savedRecipe.getId());
        assertEquals(1, savedRecipe.getIngredients().size());
        verify(recipeIngredientRepository, times(1)).saveAll(any());
    }

    /**
     * Test to save a new recipe in the database whose ingredient does not exist.
     */
    @Test
    void testSaveRecipe_IngredientNotFound() {
        Recipe recipe = new Recipe("Pasta", 2);
        RecipeIngredient ri = new RecipeIngredient();
        Ingredient fakeIngredient = new Ingredient();
        fakeIngredient.setId(999); // Non-existent
        ri.setIngredient(fakeIngredient);
        recipe.setIngredients(List.of(ri));

        when(ingredientRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> recipeDAO.save(recipe));
    }

    /**
     * Test to update a recipe in the database.
     */
    @Test
    void testUpdateRecipe_Success() {
        Recipe existingRecipe = new Recipe("Old Recipe", 4);
        existingRecipe.setId(1);
        when(recipeRepository.findById(1)).thenReturn(Optional.of(existingRecipe));

        Ingredient tomato = new Ingredient("Tomato", Category.VEGETABLE);
        tomato.setId(1);
        when(ingredientRepository.findById(1)).thenReturn(Optional.of(tomato));

        Recipe updatedRecipe = new Recipe("Updated Recipe", 3);
        RecipeIngredient ri = new RecipeIngredient();
        ri.setIngredient(tomato);
        ri.setQuantity("150g");
        updatedRecipe.setIngredients(List.of(ri));

        Recipe result = recipeDAO.update(1, updatedRecipe);

        assertEquals("Updated Recipe", result.getName());
        assertEquals(3, result.getServings());
        assertEquals(1, result.getIngredients().size());
        verify(recipeIngredientRepository, times(1)).deleteByRecipeId(1);
        verify(recipeIngredientRepository, times(1)).saveAll(any());
    }

    /**
     * Test to update a recipe in the database that does not exist.
     */
    @Test
    void testUpdateRecipe_RecipeNotFound() {
        when(recipeRepository.findById(1)).thenReturn(Optional.empty());
        Recipe updatedRecipe = new Recipe("Updated Recipe", 3);

        assertThrows(ResourceNotFoundException.class, () -> recipeDAO.update(1, updatedRecipe));
    }

    /**
     * Test to update a recipe in the database with an ingredient that does not exist.
     */
    @Test
    void testUpdateRecipe_IngredientNotFound() {
        Recipe existingRecipe = new Recipe("Old Recipe", 4);
        existingRecipe.setId(1);
        when(recipeRepository.findById(1)).thenReturn(Optional.of(existingRecipe));

        Recipe updatedRecipe = new Recipe("Updated Recipe", 3);
        Ingredient fakeIngredient = new Ingredient();
        fakeIngredient.setId(999);
        RecipeIngredient ri = new RecipeIngredient();
        ri.setIngredient(fakeIngredient);
        updatedRecipe.setIngredients(List.of(ri));

        when(ingredientRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> recipeDAO.update(1, updatedRecipe));
    }

    /**
     * Test to delete a recipe in the database by id..
     */
    @Test
    void testDeleteRecipeById() {
        recipeDAO.deleteById(1);

        verify(recipeIngredientRepository, times(1)).deleteByRecipeId(1);
        verify(recipeRepository, times(1)).deleteById(1);
    }

    /**
     * Test to search for a recipe in the database.
     */
    @Test
    void testSearchRecipes() {
        Ingredient tomato = new Ingredient("Tomato", Category.VEGETABLE);
        Recipe recipe = new Recipe("Pasta", 2);
        RecipeIngredient ri = new RecipeIngredient(recipe, tomato, "100g");
        recipe.setIngredients(List.of(ri));

        when(recipeRepository.searchRecipes(
                anyList(), anyList(), anyLong(), anyString(), anyBoolean(), anyBoolean())
        ).thenReturn(List.of(recipe));

        List<Recipe> result = recipeDAO.searchRecipes(
                List.of("Tomato"), List.of("Meat"), 1L, "%easy%", false, false
        );

        assertEquals(1, result.size());
        assertEquals("Pasta", result.get(0).getName());
        assertEquals(1, result.get(0).getIngredients().size());
    }
}

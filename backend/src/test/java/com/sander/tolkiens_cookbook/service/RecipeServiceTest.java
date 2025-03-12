package com.sander.tolkiens_cookbook.service;

import com.sander.tolkiens_cookbook.dto.RecipeDTO;
import com.sander.tolkiens_cookbook.exception.ResourceNotFoundException;
import com.sander.tolkiens_cookbook.model.Category;
import com.sander.tolkiens_cookbook.model.Ingredient;
import com.sander.tolkiens_cookbook.model.Recipe;
import com.sander.tolkiens_cookbook.model.RecipeIngredient;
import com.sander.tolkiens_cookbook.repository.RecipeDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link RecipeService}.
 */
class RecipeServiceTest {

    @InjectMocks
    private RecipeService recipeService;

    @Mock
    private RecipeDAO recipeDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test for getAllRecipes
     */
    @Test
    void testGetAllRecipes() {
        Recipe recipe1 = new Recipe("Pasta", 2);
        Recipe recipe2 = new Recipe("Salad", 1);

        when(recipeDAO.findAll()).thenReturn(List.of(recipe1, recipe2));

        List<RecipeDTO> result = recipeService.getAllRecipes();

        assertEquals(2, result.size());
        assertEquals("Pasta", result.get(0).getName());
        assertEquals("Salad", result.get(1).getName());
        verify(recipeDAO, times(1)).findAll();
    }

    /**
     * Test for getRecipeById - Found
     */
    @Test
    void testGetRecipeById_Found() {
        Recipe recipe = new Recipe("Pasta", 2);
        recipe.setId(1);

        when(recipeDAO.findById(1)).thenReturn(Optional.of(recipe));

        RecipeDTO result = recipeService.getRecipeById(1);

        assertEquals("Pasta", result.getName());
        verify(recipeDAO, times(1)).findById(1);
    }

    /**
     * Test for getRecipeById - Not Found
     */
    @Test
    void testGetRecipeById_NotFound() {
        when(recipeDAO.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> recipeService.getRecipeById(1));
        verify(recipeDAO, times(1)).findById(1);
    }

    /**
     * Test for searchRecipes with all filters null
     */
    @Test
    void testSearchRecipes_NoFilters() {
        Recipe recipe = new Recipe("Pasta", 2);

        when(recipeDAO.searchRecipes(
                Collections.emptyList(),
                Collections.emptyList(),
                0,
                null,
                true,
                true,
                null
        )).thenReturn(List.of(recipe));

        List<RecipeDTO> result = recipeService.searchRecipes(null, null, null, null, null);

        assertEquals(1, result.size());
        assertEquals("Pasta", result.get(0).getName());

        verify(recipeDAO, times(1)).searchRecipes(
                Collections.emptyList(),
                Collections.emptyList(),
                0,
                null,
                true,
                true,
                null
        );
    }

    /**
     * Test for searchRecipes with vegetarian filter true
     */
    @Test
    void testSearchRecipes_VegetarianFilter() {
        Ingredient spinach = new Ingredient("Spinach", Category.VEGETABLE);

        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setIngredient(spinach);

        Recipe vegetarianRecipe = new Recipe("Salad", 1);
        vegetarianRecipe.setIngredients(List.of(recipeIngredient));

        when(recipeDAO.searchRecipes(
                anyList(), anyList(), anyLong(), any(), anyBoolean(), anyBoolean(), any()
        )).thenReturn(List.of(vegetarianRecipe));

        List<RecipeDTO> result = recipeService.searchRecipes(null, null, null, null, true);

        assertEquals(1, result.size());
        assertEquals("Salad", result.get(0).getName());

        verify(recipeDAO, times(1)).searchRecipes(
                anyList(), anyList(), anyLong(), any(), anyBoolean(), anyBoolean(), any()
        );
    }

    /**
     * Test for searchRecipes with vegetarian filter false
     */
    @Test
    void testSearchRecipes_NonVegetarianFilter() {
        Ingredient chicken = new Ingredient("Chicken", Category.MEAT);

        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setIngredient(chicken);

        Recipe nonVegetarianRecipe = new Recipe("Chicken Curry", 2);
        nonVegetarianRecipe.setIngredients(List.of(recipeIngredient));

        when(recipeDAO.searchRecipes(
                anyList(), anyList(), anyLong(), any(), anyBoolean(), anyBoolean(), any()
        )).thenReturn(List.of(nonVegetarianRecipe));

        List<RecipeDTO> result = recipeService.searchRecipes(null, null, null, null, false);

        assertEquals(1, result.size());
        assertEquals("Chicken Curry", result.get(0).getName());

        verify(recipeDAO, times(1)).searchRecipes(
                anyList(), anyList(), anyLong(), any(), anyBoolean(), anyBoolean(), any()
        );
    }

    /**
     * Test for save Recipe
     */
    @Test
    void testSaveRecipe() {
        Recipe recipe = new Recipe("Pizza", 3);
        recipe.setIngredients(new ArrayList<>());

        when(recipeDAO.save(recipe)).thenReturn(recipe);

        RecipeDTO result = recipeService.save(recipe);

        assertEquals("Pizza", result.getName());
        assertEquals(3, result.getServings());
        verify(recipeDAO, times(1)).save(recipe);
    }

    /**
     * Test for updateRecipe
     */
    @Test
    void testUpdateRecipe() {
        Recipe updatedRecipe = new Recipe("Updated Pizza", 4);
        when(recipeDAO.update(1, updatedRecipe)).thenReturn(updatedRecipe);

        RecipeDTO result = recipeService.updateRecipe(1, updatedRecipe);

        assertEquals("Updated Pizza", result.getName());
        assertEquals(4, result.getServings());
        verify(recipeDAO, times(1)).update(1, updatedRecipe);
    }

    /**
     * Test for deleteRecipe
     */
    @Test
    void testDeleteRecipe() {
        doNothing().when(recipeDAO).deleteById(1);

        recipeService.deleteRecipe(1);

        verify(recipeDAO, times(1)).deleteById(1);
    }
}
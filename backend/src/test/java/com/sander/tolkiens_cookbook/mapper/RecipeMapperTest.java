package com.sander.tolkiens_cookbook.mapper;

import com.sander.tolkiens_cookbook.dto.RecipeDTO;
import com.sander.tolkiens_cookbook.dto.RecipeIngredientDTO;
import com.sander.tolkiens_cookbook.model.Category;
import com.sander.tolkiens_cookbook.model.Ingredient;
import com.sander.tolkiens_cookbook.model.Recipe;
import com.sander.tolkiens_cookbook.model.RecipeIngredient;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link RecipeMapper}.
 */
public class RecipeMapperTest {

    /**
     * Test that a Recipe entity is correctly mapped to a RecipeDTO, including vegetarian status and ingredients.
     */
    @Test
    void testToDTO() {
        // Set up Ingredient
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1);
        ingredient.setName("Tomato");
        ingredient.setCategory(Category.VEGETABLE); // Vegetarian ingredient

        // Set up RecipeIngredient
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setQuantity("200g");

        // Set up Recipe with the Ingredient
        Recipe recipe = new Recipe();
        recipe.setId(10);
        recipe.setName("Tomato Soup");
        recipe.setServings(2);
        recipe.setInstructions("Boil tomatoes and blend.");
        recipe.setIngredients(Collections.singletonList(recipeIngredient));

        // Act
        RecipeDTO dto = RecipeMapper.toDTO(recipe);

        // Assert
        assertEquals(10, dto.getId(), "ID should be mapped correctly");
        assertEquals("Tomato Soup", dto.getName(), "Name should be mapped correctly");
        assertEquals(2, dto.getServings(), "Servings should be mapped correctly");
        assertEquals("Boil tomatoes and blend.", dto.getInstructions(), "Instructions should be mapped correctly");
        assertTrue(dto.isVegetarian(), "Recipe should be vegetarian because all ingredients are vegetarian");
        assertEquals(1, dto.getIngredients().size(), "There should be 1 ingredient");

        RecipeIngredientDTO ingredientDTO = dto.getIngredients().get(0);
        assertEquals(1, ingredientDTO.getId(), "Ingredient ID should be mapped correctly");
        assertEquals("Tomato", ingredientDTO.getName(), "Ingredient name should be mapped correctly");
        assertEquals("200g", ingredientDTO.getQuantity(), "Ingredient quantity should be mapped correctly");
    }

    /**
     * Test that a Recipe entity with a non-vegetarian ingredient is mapped and vegetarian flag is false.
     */
    @Test
    void testToDTO_NonVegetarian() {
        // Set up non-vegetarian Ingredient
        Ingredient chicken = new Ingredient();
        chicken.setId(2);
        chicken.setName("Chicken");
        chicken.setCategory(Category.MEAT); // Non-vegetarian

        // Set up RecipeIngredient
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setIngredient(chicken);
        recipeIngredient.setQuantity("300g");

        // Set up Recipe
        Recipe recipe = new Recipe();
        recipe.setId(20);
        recipe.setName("Chicken Soup");
        recipe.setServings(4);
        recipe.setInstructions("Cook chicken and add broth.");
        recipe.setIngredients(Collections.singletonList(recipeIngredient));

        // Act
        RecipeDTO dto = RecipeMapper.toDTO(recipe);

        // Assert
        assertEquals(20, dto.getId(), "ID should be mapped correctly");
        assertEquals("Chicken Soup", dto.getName(), "Name should be mapped correctly");
        assertEquals(4, dto.getServings(), "Servings should be mapped correctly");
        assertEquals("Cook chicken and add broth.", dto.getInstructions(), "Instructions should be mapped correctly");
        assertFalse(dto.isVegetarian(), "Recipe should not be vegetarian because it contains meat");
        assertEquals(1, dto.getIngredients().size(), "There should be 1 ingredient");

        RecipeIngredientDTO ingredientDTO = dto.getIngredients().get(0);
        assertEquals(2, ingredientDTO.getId(), "Ingredient ID should be mapped correctly");
        assertEquals("Chicken", ingredientDTO.getName(), "Ingredient name should be mapped correctly");
        assertEquals("300g", ingredientDTO.getQuantity(), "Ingredient quantity should be mapped correctly");
    }
}

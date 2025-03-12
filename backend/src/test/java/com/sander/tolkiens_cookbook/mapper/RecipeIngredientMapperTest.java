package com.sander.tolkiens_cookbook.mapper;

import com.sander.tolkiens_cookbook.dto.RecipeIngredientDTO;
import com.sander.tolkiens_cookbook.model.Category;
import com.sander.tolkiens_cookbook.model.Ingredient;
import com.sander.tolkiens_cookbook.model.RecipeIngredient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link RecipeIngredientMapper}.
 */
public class RecipeIngredientMapperTest {

    /**
     * Test that a RecipeIngredient entity is correctly mapped to a RecipeIngredientDTO.
     */
    @Test
    void testToDTO() {
        // Set up Ingredient
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1);
        ingredient.setName("Tomato");
        ingredient.setCategory(Category.VEGETABLE);

        // Set up RecipeIngredient
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setQuantity("200g");

        // Act
        RecipeIngredientDTO dto = RecipeIngredientMapper.toDTO(recipeIngredient);

        // Assert
        assertEquals(1, dto.getId(), "Ingredient ID should be mapped correctly");
        assertEquals("Tomato", dto.getName(), "Ingredient name should be mapped correctly");
        assertEquals("200g", dto.getQuantity(), "Ingredient quantity should be mapped correctly");
    }

    /**
     * Test that the mapper throws a NullPointerException if RecipeIngredient is missing an ingredient.
     */
    @Test
    void testToDTO_ThrowsExceptionWhenIngredientIsNull() {
        // Set up RecipeIngredient without Ingredient
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setQuantity("100g");

        // Act & Assert
        assertThrows(NullPointerException.class, () -> RecipeIngredientMapper.toDTO(recipeIngredient),
                "Mapping should throw NullPointerException if ingredient is null");
    }
}

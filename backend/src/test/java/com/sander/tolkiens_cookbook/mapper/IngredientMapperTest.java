package com.sander.tolkiens_cookbook.mapper;

import com.sander.tolkiens_cookbook.dto.IngredientDTO;
import com.sander.tolkiens_cookbook.model.Category;
import com.sander.tolkiens_cookbook.model.Ingredient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link IngredientMapper}.
 */
public class IngredientMapperTest {

    /**
     * Test that an Ingredient entity is correctly mapped to an IngredientDTO.
     */
    @Test
    void testToDTO() {
        // Create an Ingredient entity
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1);
        ingredient.setName("Tomato");
        ingredient.setCategory(Category.VEGETABLE);

        // Act
        IngredientDTO dto = IngredientMapper.toDTO(ingredient);

        // Assert
        assertEquals(1, dto.getId(), "ID should be mapped correctly");
        assertEquals("Tomato", dto.getName(), "Name should be mapped correctly");
        assertEquals("VEGETABLE", dto.getCategory(), "Category should be mapped as String correctly");
        assertTrue(dto.isVegetarian(), "Vegetarian field should be mapped correctly based on category");
    }

    /**
     * Test that the isVegetarian field is correctly set for a non-vegetarian category.
     */
    @Test
    void testToDTO_NonVegetarian() {
        // Create a meat ingredient
        Ingredient ingredient = new Ingredient();
        ingredient.setId(2);
        ingredient.setName("Chicken");
        ingredient.setCategory(Category.MEAT);

        // Act
        IngredientDTO dto = IngredientMapper.toDTO(ingredient);

        // Assert
        assertEquals(2, dto.getId(), "ID should be mapped correctly");
        assertEquals("Chicken", dto.getName(), "Name should be mapped correctly");
        assertEquals("MEAT", dto.getCategory(), "Category should be mapped as String correctly");
        assertFalse(dto.isVegetarian(), "Vegetarian should be false for meat");
    }
}

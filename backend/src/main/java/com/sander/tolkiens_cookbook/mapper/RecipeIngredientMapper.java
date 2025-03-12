package com.sander.tolkiens_cookbook.mapper;

import com.sander.tolkiens_cookbook.dto.RecipeIngredientDTO;
import com.sander.tolkiens_cookbook.model.RecipeIngredient;

/**
 * Mapper class for converting {@link RecipeIngredient} entities to {@link RecipeIngredientDTO} objects.
 *
 * This class is used to separate entity logic from data transfer logic, enabling
 * a clean and maintainable structure for sending recipe-ingredient data to clients.
 */
public class RecipeIngredientMapper {

    /**
     * Converts a {@link RecipeIngredient} entity to a {@link RecipeIngredientDTO}.
     *
     * @param recipeIngredient the {@code RecipeIngredient} entity to convert
     * @return a corresponding {@code RecipeIngredientDTO} containing the ingredient's ID, name, and quantity
     */
    public static RecipeIngredientDTO toDTO(RecipeIngredient recipeIngredient) {
        return new RecipeIngredientDTO(
                recipeIngredient.getIngredient().getId(),
                recipeIngredient.getIngredient().getName(),
                recipeIngredient.getQuantity()
        );
    }
}
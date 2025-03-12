package com.sander.tolkiens_cookbook.mapper;

import com.sander.tolkiens_cookbook.dto.RecipeDTO;
import com.sander.tolkiens_cookbook.model.Recipe;

/**
 * Mapper class for converting {@link Recipe} entities to {@link RecipeDTO} objects.
 *
 * <p>This class is used to separate entity logic from data transfer logic, enabling
 * a clean and maintainable structure for sending recipe data to clients.</p>
 */
public class RecipeMapper {

    /**
     * Converts a {@link Recipe} entity to a {@link RecipeDTO}.
     *
     * @param recipe the {@code Recipe} entity to convert
     * @return a {@code RecipeDTO} containing the recipe's ID, name, servings, vegetarian status, ingredients, and instructions
     */
    public static RecipeDTO toDTO(Recipe recipe) {
        return new RecipeDTO(
                recipe.getId(),
                recipe.getName(),
                recipe.getServings(),
                recipe.isVegetarian(),
                recipe.getRecipeIngredientsDTO(),
                recipe.getInstructions()
        );
    }
}
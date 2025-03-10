package com.sander.tolkiens_cookbook.mapper;

import com.sander.tolkiens_cookbook.dto.RecipeDTO;
import com.sander.tolkiens_cookbook.model.Recipe;

public class RecipeMapper {

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

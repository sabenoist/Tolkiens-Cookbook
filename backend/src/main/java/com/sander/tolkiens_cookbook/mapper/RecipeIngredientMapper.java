package com.sander.tolkiens_cookbook.mapper;

import com.sander.tolkiens_cookbook.dto.RecipeIngredientDTO;
import com.sander.tolkiens_cookbook.model.RecipeIngredient;

public class RecipeIngredientMapper {

    public static RecipeIngredientDTO toDTO(RecipeIngredient recipeIngredient) {
        return new RecipeIngredientDTO(
                recipeIngredient.getIngredient().getId(),
                recipeIngredient.getIngredient().getName(),
                recipeIngredient.getQuantity()
        );
    }
}

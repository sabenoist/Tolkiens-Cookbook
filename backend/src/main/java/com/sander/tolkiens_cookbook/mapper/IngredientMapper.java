package com.sander.tolkiens_cookbook.mapper;

import com.sander.tolkiens_cookbook.entity.Ingredient;
import com.sander.tolkiens_cookbook.dto.IngredientDTO;

public class IngredientMapper {

    public static IngredientDTO toDTO(Ingredient ingredient) {
        return new IngredientDTO(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getCategory().toString(),
                ingredient.isVegetarian()
        );
    }
}

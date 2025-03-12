package com.sander.tolkiens_cookbook.mapper;

import com.sander.tolkiens_cookbook.model.Ingredient;
import com.sander.tolkiens_cookbook.dto.IngredientDTO;

/**
 * Mapper class for converting {@link Ingredient} entities to {@link IngredientDTO} objects.
 *
 * <p>This class is used to separate entity logic from data transfer logic, enabling
 * a clean and maintainable structure for sending ingredient data to clients.</p>
 */
public class IngredientMapper {

    /**
     * Converts an {@link Ingredient} entity to an {@link IngredientDTO}.
     *
     * @param ingredient the {@code Ingredient} entity to convert
     * @return a corresponding {@code IngredientDTO} containing the ingredient's data
     */
    public static IngredientDTO toDTO(Ingredient ingredient) {
        return new IngredientDTO(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getCategory().toString(),
                ingredient.isVegetarian()
        );
    }
}
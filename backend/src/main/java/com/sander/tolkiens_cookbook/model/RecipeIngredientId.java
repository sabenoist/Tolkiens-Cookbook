package com.sander.tolkiens_cookbook.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key for the {@link RecipeIngredient} entity, combining
 * a recipe ID and an ingredient ID.
 *
 * This class is marked as {@link Embeddable} to be used as an embedded ID in JPA entities,
 * and implements {@link Serializable} as required for composite keys.
 *
 * @see RecipeIngredient
 */
@Embeddable
public class RecipeIngredientId implements Serializable {

    /**
     * The ID of the recipe associated with the ingredient.
     */
    private int recipeId;

    /**
     * The ID of the ingredient associated with the recipe.
     */
    private int ingredientId;

    /**
     * Default no-argument constructor required by JPA.
     */
    public RecipeIngredientId() {}

    /**
     * Constructs a {@code RecipeIngredientId} with the specified recipe and ingredient IDs.
     *
     * @param recipeId     the ID of the recipe.
     * @param ingredientId the ID of the ingredient.
     */
    public RecipeIngredientId(int recipeId, int ingredientId) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
    }

    /**
     * Compares this composite key to another object for equality.
     * Two composite keys are equal if both their recipe and ingredient IDs are equal.
     *
     * @param o the other object to compare.
     * @return {@code true} if the objects are equal; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredientId that = (RecipeIngredientId) o;
        return recipeId == that.recipeId && ingredientId == that.ingredientId;
    }

    /**
     * Generates a hash code based on the recipe and ingredient IDs.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(recipeId, ingredientId);
    }

    // Getters and Setters
    public int getRecipeId() { return recipeId; }
    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }

    public int getIngredientId() { return ingredientId; }
    public void setIngredientId(int ingredientId) { this.ingredientId = ingredientId; }
}
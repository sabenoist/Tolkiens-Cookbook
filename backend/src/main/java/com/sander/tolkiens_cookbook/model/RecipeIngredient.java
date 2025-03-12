package com.sander.tolkiens_cookbook.model;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Represents the many-to-many relationship between {@link Recipe} and {@link Ingredient},
 * including the quantity of each ingredient used in a recipe.
 *
 * <p>This entity is mapped to the "recipe_ingredients" table in the database and
 * uses a composite key defined in {@link RecipeIngredientId}.</p>
 *
 * @see Recipe
 * @see Ingredient
 * @see RecipeIngredientId
 */
@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient {

    /**
     * Composite primary key consisting of recipe ID and ingredient ID.
     */
    @EmbeddedId
    private RecipeIngredientId id;

    /**
     * Reference to the associated recipe.
     */
    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    /**
     * Reference to the associated ingredient.
     */
    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    /**
     * The quantity of the ingredient used in the recipe (e.g., "200g", "1 tbsp").
     */
    @Column(name = "quantity")
    private String quantity;

    /**
     * Default no-argument constructor for JPA.
     */
    public RecipeIngredient() {}

    /**
     * Constructs a {@link RecipeIngredient} with the specified recipe, ingredient, and quantity.
     * Also generates the composite key automatically based on the recipe and ingredient IDs.
     *
     * @param recipe     the associated recipe.
     * @param ingredient the associated ingredient.
     * @param quantity   the quantity of the ingredient in the recipe.
     */
    public RecipeIngredient(Recipe recipe, Ingredient ingredient, String quantity) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.id = new RecipeIngredientId(recipe.getId(), ingredient.getId());
    }

    /**
     * Gets the ID of the associated ingredient.
     * <p>Convenience method for accessing the ingredient's ID directly.</p>
     *
     * @return the ingredient's ID.
     */
    public int getIngredientId() {
        return ingredient.getId();
    }

    /**
     * Checks equality based on the composite key.
     *
     * @param o the other object to compare.
     * @return {@code true} if both objects have the same composite key, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredient that = (RecipeIngredient) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Generates a hash code based on the composite key.
     *
     * @return the hash code of the composite key.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Getters and Setters
    public RecipeIngredientId getId() { return id; }
    public void setId(RecipeIngredientId id) { this.id = id; }

    public Recipe getRecipe() { return recipe; }
    public void setRecipe(Recipe recipe) { this.recipe = recipe; }

    public Ingredient getIngredient() { return ingredient; }
    public void setIngredient(Ingredient ingredient) { this.ingredient = ingredient; }

    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
}
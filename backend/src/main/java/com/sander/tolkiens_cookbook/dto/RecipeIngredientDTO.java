package com.sander.tolkiens_cookbook.dto;

/**
 * Data Transfer Object (DTO) for transferring RecipeIngredient data between layers.
 *
 * <p>This DTO is used to represent a RecipeIngredient entity without exposing internal entity details.
 * it represents an ingredient used in a recipe, including its ID, name, and quantity.</p>
 */
public class RecipeIngredientDTO {

    /**
     * The unique identifier of the ingredient.
     */
    private int id;

    /**
     * The name of the ingredient.
     */
    private String name;

    /**
     * The quantity of the ingredient required for the recipe.
     */
    private String quantity;

    /**
     * Constructs a RecipeIngredientDTO with all fields.
     *
     * @param id             the unique identifier of the ingredient
     * @param ingredientName the name of the ingredient
     * @param quantity       the quantity of the ingredient used in the recipe
     */
    public RecipeIngredientDTO(int id, String ingredientName, String quantity) {
        this.id = id;
        this.name = ingredientName;
        this.quantity = quantity;
    }

    // Getters and Setters

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getQuantity() { return quantity; }

    public void setQuantity(String quantity) { this.quantity = quantity; }
}
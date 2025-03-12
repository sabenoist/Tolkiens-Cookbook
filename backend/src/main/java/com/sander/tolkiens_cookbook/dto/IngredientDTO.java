package com.sander.tolkiens_cookbook.dto;

/**
 * Data Transfer Object (DTO) for transferring Ingredient data between layers.
 *
 * This DTO is used to represent an Ingredient entity without exposing internal entity details.
 * It includes the ingredient's ID, name, category, and whether it is vegetarian.
 */
public class IngredientDTO {

    /**
     * The unique identifier of the ingredient.
     */
    private int id;

    /**
     * The name of the ingredient.
     */
    private String name;

    /**
     * The category to which this ingredient belongs (e.g., VEGETABLE, MEAT).
     */
    private String category;

    /**
     * Flag indicating whether this ingredient is vegetarian.
     */
    private boolean vegetarian;

    /**
     * Constructs an IngredientDTO with all fields.
     *
     * @param id          the unique identifier of the ingredient
     * @param name        the name of the ingredient
     * @param category    the category of the ingredient (as a String representation of the enum)
     * @param vegetarian  whether the ingredient is vegetarian
     */
    public IngredientDTO(int id, String name, String category, boolean vegetarian) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.vegetarian = vegetarian;
    }

    // Getters and Setters

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public boolean isVegetarian() { return vegetarian; }

    public void setVegetarian(boolean vegetarian) { this.vegetarian = vegetarian; }
}
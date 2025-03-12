package com.sander.tolkiens_cookbook.dto;

import java.util.List;

/**
 * Data Transfer Object (DTO) for transferring Recipe data between layers.
 *
 * This DTO is used to represent a Recipe entity without exposing internal entity details.
 * It includes its basic information, list of ingredients, and instructions.
 */
public class RecipeDTO {

    /**
     * The unique identifier of the recipe.
     */
    private int id;

    /**
     * The name of the recipe.
     */
    private String name;

    /**
     * The number of servings the recipe yields.
     */
    private int servings;

    /**
     * Flag indicating whether this recipe is vegetarian.
     */
    private boolean vegetarian;

    /**
     * List of ingredients used in the recipe, each with its quantity.
     */
    private List<RecipeIngredientDTO> ingredients;

    /**
     * Textual instructions on how to prepare the recipe.
     */
    private String instructions;

    /**
     * Constructs a RecipeDTO with all fields.
     *
     * @param id            the unique identifier of the recipe
     * @param name          the name of the recipe
     * @param servings      the number of servings
     * @param vegetarian    whether the recipe is vegetarian
     * @param ingredients   list of recipe ingredients with quantities
     * @param instructions  step-by-step instructions to prepare the recipe
     */
    public RecipeDTO(int id, String name, int servings, boolean vegetarian, List<RecipeIngredientDTO> ingredients, String instructions) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.vegetarian = vegetarian;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    // Getters and Setters

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getServings() { return servings; }

    public void setServings(int servings) { this.servings = servings; }

    public boolean isVegetarian() { return vegetarian; }

    public void setVegetarian(boolean vegetarian) { this.vegetarian = vegetarian; }

    public List<RecipeIngredientDTO> getIngredients() { return ingredients; }

    public void setIngredients(List<RecipeIngredientDTO> ingredients) { this.ingredients = ingredients; }

    public String getInstructions() { return instructions; }

    public void setInstructions(String instructions) { this.instructions = instructions; }
}
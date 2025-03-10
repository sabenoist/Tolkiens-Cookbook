package com.sander.tolkiens_cookbook.dto;

import java.util.List;

public class RecipeDTO {
    private int id;
    private String name;
    private int servings;
    private boolean vegetarian;
    private List<RecipeIngredientDTO> ingredients;
    private String instructions;

    // Constructor
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

    public String getInstructions() { return instructions; }  // Getter for instructions
    public void setInstructions(String instructions) { this.instructions = instructions; }  // Setter for instructions
}

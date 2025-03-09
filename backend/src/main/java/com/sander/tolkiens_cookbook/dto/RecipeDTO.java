package com.sander.tolkiens_cookbook.dto;

import java.util.List;

public class RecipeDTO {
    private int id;
    private String name;
    private int servings; // Include servings
    private List<RecipeIngredientDTO> ingredients; // List of ingredients

    // Constructor
    public RecipeDTO(int id, String name, int servings, List<RecipeIngredientDTO> ingredients) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.ingredients = ingredients;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getServings() { return servings; }
    public void setServings(int servings) { this.servings = servings; }

    public List<RecipeIngredientDTO> getIngredients() { return ingredients; }
    public void setIngredients(List<RecipeIngredientDTO> ingredients) { this.ingredients = ingredients; }
}

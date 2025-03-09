package com.sander.tolkiens_cookbook.dto;

public class RecipeIngredientDTO {
    private String quantity;
    private String ingredientName;

    // Constructor
    public RecipeIngredientDTO(String quantity, String ingredientName) {
        this.quantity = quantity;
        this.ingredientName = ingredientName;
    }

    // Getters and Setters
    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }

    public String getIngredientName() { return ingredientName; }
    public void setIngredientName(String ingredientName) { this.ingredientName = ingredientName; }
}

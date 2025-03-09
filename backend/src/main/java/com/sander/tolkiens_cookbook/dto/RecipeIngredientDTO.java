package com.sander.tolkiens_cookbook.dto;

public class RecipeIngredientDTO {
    private String quantity;
    private int ingredientId;

    // Constructor
    public RecipeIngredientDTO(String quantity, int ingredientId) {
        this.quantity = quantity;
        this.ingredientId = ingredientId;
    }

    // Getters and Setters
    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }

    public int getIngredientId() { return ingredientId; }
    public void setIngredientId(int ingredientId) { this.ingredientId = ingredientId; }
}

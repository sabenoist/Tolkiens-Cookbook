package com.sander.tolkiens_cookbook.dto;

public class RecipeIngredientDTO {
    private int id;
    private String name;
    private String quantity;

    // Constructor
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

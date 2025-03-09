package com.sander.tolkiens_cookbook.dto;

public class IngredientDTO {
    private int id;
    private String name;
    private String category;
    private boolean vegetarian;

    // Constructor
    public IngredientDTO(int id, String name, String category, boolean vegetarian) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.vegetarian = vegetarian;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }
}

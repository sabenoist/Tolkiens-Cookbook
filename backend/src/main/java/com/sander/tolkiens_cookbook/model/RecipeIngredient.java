package com.sander.tolkiens_cookbook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient {

    @EmbeddedId
    //@JsonIgnore
    private RecipeIngredientId id;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    //@JsonIgnore  // Prevents infinite recursion by ignoring recipe serialization
    private Recipe recipe;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    //@JsonIgnore  // Prevents full ingredient serialization
    private Ingredient ingredient;

    @Column(name = "quantity")
    private String quantity;

    public RecipeIngredient() {}

    public RecipeIngredient(Recipe recipe, Ingredient ingredient, String quantity) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.id = new RecipeIngredientId(recipe.getId(), ingredient.getId());
    }

    public RecipeIngredientId getId() { return id; }
    public void setId(RecipeIngredientId id) { this.id = id; }

    public Recipe getRecipe() { return recipe; }
    public void setRecipe(Recipe recipe) { this.recipe = recipe; }

    public Ingredient getIngredient() { return ingredient; }
    public void setIngredient(Ingredient ingredient) { this.ingredient = ingredient; }

    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }

    public int getIngredientId() {
        return ingredient.getId();
    }
}

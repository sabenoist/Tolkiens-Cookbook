package com.sander.tolkiens_cookbook.model;

import com.sander.tolkiens_cookbook.dto.RecipeIngredientDTO;
import com.sander.tolkiens_cookbook.mapper.RecipeIngredientMapper;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a recipe containing various ingredients, serving size, instructions,
 * and metadata about whether it is vegetarian.
 *
 * <p>The {@link RecipeIngredient} class is used to represent the relationship between
 * recipes and ingredients, including additional information like quantity.</p>
 *
 * @see RecipeIngredient
 * @see Ingredient
 */
@Entity
@Table(name = "recipes")
public class Recipe {

    /**
     * The unique identifier of the recipe.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private int id;

    /**
     * The name of the recipe. Must be unique and not null.
     */
    @Column(name = "recipe_name", nullable = false, unique = true)
    private String name;

    /**
     * The number of servings this recipe provides.
     */
    @Column(name = "servings", nullable = false)
    private int servings;

    /**
     * The preparation and cooking instructions for the recipe.
     */
    @Column(name = "instructions", nullable = false, columnDefinition = "TEXT")
    private String instructions;

    /**
     * The list of {@link RecipeIngredient} relationships representing the ingredients used in this recipe.
     */
    @OneToMany(mappedBy = "recipe")
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    /**
     * Default no-argument constructor for JPA.
     */
    public Recipe() {}

    /**
     * Constructs a new Recipe with a specified name and servings count.
     *
     * @param name     the name of the recipe.
     * @param servings the number of servings the recipe makes.
     */
    public Recipe(String name, int servings) {
        this.name = name;
        this.servings = servings;
    }

    /**
     * Checks if this recipe is vegetarian.
     *
     * <p>This method evaluates all ingredients used in the recipe. If all ingredients are vegetarian,
     * the recipe is considered vegetarian.</p>
     *
     * @return {@code true} if all ingredients are vegetarian, {@code false} otherwise.
     */
    @Transient
    public boolean isVegetarian() {
        return this.getIngredients().stream()
                .allMatch(recipeIngredient -> recipeIngredient.getIngredient().isVegetarian());
    }

    /**
     * Converts the list of {@link RecipeIngredient} entities into a list of {@link RecipeIngredientDTO} objects.
     *
     * <p>This method is used to expose a simplified version of the recipe ingredients, typically for API responses.</p>
     *
     * @return a list of {@link RecipeIngredientDTO} representing the ingredients and their quantities.
     */
    @Transient
    public List<RecipeIngredientDTO> getRecipeIngredientsDTO() {
        return this.getIngredients().stream()
                .map(RecipeIngredientMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getServings() { return servings; }
    public void setServings(int servings) { this.servings = servings; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public List<RecipeIngredient> getIngredients() { return ingredients; }
    public void setIngredients(List<RecipeIngredient> ingredients) { this.ingredients = ingredients; }
}
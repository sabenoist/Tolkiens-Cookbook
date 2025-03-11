package com.sander.tolkiens_cookbook.model;

import com.sander.tolkiens_cookbook.dto.RecipeIngredientDTO;
import com.sander.tolkiens_cookbook.mapper.RecipeIngredientMapper;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private int id;

    @Column(name = "recipe_name", nullable = false, unique = true)
    private String name;

    @Column(name = "servings", nullable = false)
    private int servings;

    @Column(name = "instructions", nullable = false, columnDefinition = "TEXT")
    private String instructions;

    @OneToMany(mappedBy = "recipe", orphanRemoval = true)
    //@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredient> ingredients;

    public Recipe() {}

    public Recipe(String name, int servings) {
        this.name = name;
        this.servings = servings;
    }

    @Transient
    public boolean isVegetarian() {
        return this.getIngredients().stream()
                .allMatch(recipeIngredient -> recipeIngredient.getIngredient().isVegetarian());
    }

    @Transient
    public List<RecipeIngredientDTO> getRecipeIngredientsDTO() {
        return this.getIngredients().stream()
                .map(RecipeIngredientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getServings() { return servings; }
    public void setServings(int servings) { this.servings = servings; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public Set<RecipeIngredient> getIngredients() { return ingredients; }
    public void setIngredients(Set<RecipeIngredient> ingredients) { this.ingredients = ingredients; }
}

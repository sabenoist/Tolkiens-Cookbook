package com.sander.tolkiens_cookbook.entity;

import jakarta.persistence.*;
import java.util.Set;

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

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredient> ingredients;

    public Recipe() {}

    public Recipe(String name, int servings) {
        this.name = name;
        this.servings = servings;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getServings() { return servings; }
    public void setServings(int servings) { this.servings = servings; }

    public Set<RecipeIngredient> getIngredients() { return ingredients; }
    public void setIngredients(Set<RecipeIngredient> ingredients) { this.ingredients = ingredients; }
}

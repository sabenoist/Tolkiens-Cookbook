package com.sander.tolkiens_cookbook.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private int id;

    @Column(name = "ingredient_name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredient> recipes = new HashSet<>();

    // Constructors
    public Ingredient() {}

    public Ingredient(String name) {
        this.name = name;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<RecipeIngredient> getRecipes() { return recipes; }
    public void setRecipes(Set<RecipeIngredient> recipes) { this.recipes = recipes; }
}
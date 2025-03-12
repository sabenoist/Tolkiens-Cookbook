package com.sander.tolkiens_cookbook.model;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "ingredient")
    private Set<RecipeIngredient> recipes = new HashSet<>();

    public Ingredient() {}

    public Ingredient(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Set<RecipeIngredient> getRecipes() { return recipes; }
    public void setRecipes(Set<RecipeIngredient> recipes) { this.recipes = recipes; }

    @Transient
    public boolean isVegetarian() {
        switch (this.category) {
            case VEGETABLE:
            case FRUIT:
            case GRAIN:
            case SEED:
            case DAIRY:
            case OTHER:
                return true;
            case MEAT:
            case FISH:
            default:
                return false;
        }
    }
}

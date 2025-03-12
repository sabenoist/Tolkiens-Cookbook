package com.sander.tolkiens_cookbook.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an ingredient used in recipes.
 *
 * <p>Each ingredient has a name, belongs to a category, and can be associated
 * with multiple recipes through the {@link RecipeIngredient} entity.</p>
 *
 * <p>The class also provides a helper method to determine whether an ingredient
 * is considered vegetarian based on its category.</p>
 *
 * @see RecipeIngredient
 * @see Category
 */
@Entity
@Table(name = "ingredients")
public class Ingredient {

    /**
     * The unique identifier of the ingredient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private int id;

    /**
     * The name of the ingredient. Must be unique and not null.
     */
    @Column(name = "ingredient_name", nullable = false, unique = true)
    private String name;

    /**
     * The category of the ingredient.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    /**
     * A set of {@link RecipeIngredient} relationships indicating which recipes use this ingredient.
     */
    @OneToMany(mappedBy = "ingredient")
    private Set<RecipeIngredient> recipes = new HashSet<>();

    /**
     * Default no-argument constructor for JPA.
     */
    public Ingredient() {}

    /**
     * Constructs a new Ingredient with the specified name and category.
     *
     * @param name     the name of the ingredient.
     * @param category the category to which the ingredient belongs.
     */
    public Ingredient(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    /**
     * Determines if the ingredient is vegetarian based on its category.
     *
     * @return {@code true} if the ingredient is vegetarian, {@code false} otherwise.
     */
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

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Set<RecipeIngredient> getRecipes() { return recipes; }
    public void setRecipes(Set<RecipeIngredient> recipes) { this.recipes = recipes; }
}
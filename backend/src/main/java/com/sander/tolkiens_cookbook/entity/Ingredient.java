package com.sander.tolkiens_cookbook.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ingredients")  // Ensure this matches the actual table name in PostgreSQL
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

    public Ingredient() {}

    public Ingredient(String name, Category category) {
        this.name = name;
        this.category = category;
    }

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

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

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
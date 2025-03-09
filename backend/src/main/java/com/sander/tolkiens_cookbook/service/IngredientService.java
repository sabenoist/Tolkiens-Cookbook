package com.sander.tolkiens_cookbook.service;

import com.sander.tolkiens_cookbook.entity.Ingredient;
import com.sander.tolkiens_cookbook.dao.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Optional<Ingredient> getIngredientById(int id) {
        return ingredientRepository.findById(id);
    }

    public Ingredient getIngredientByName(String name) {
        return ingredientRepository.findByName(name);
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(int id) {
        ingredientRepository.deleteById(id);
    }

    public void resetIngredients() {
        ingredientRepository.clearIngredients();
    }
}
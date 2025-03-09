package com.sander.tolkiens_cookbook.service;

import com.sander.tolkiens_cookbook.entity.Ingredient;
import com.sander.tolkiens_cookbook.dao.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Transactional
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Transactional
    public Ingredient getIngredientById(int id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    @Transactional
    public Ingredient getIngredientByName(String name) {
        return ingredientRepository.findByName(name);
    }

    @Transactional
    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Transactional
    public void deleteIngredient(int id) {
        ingredientRepository.deleteById(id);
    }

    @Transactional
    public void resetIngredients() {
        ingredientRepository.clearIngredients();
    }
}
package com.sander.tolkiens_cookbook.repository;

import com.sander.tolkiens_cookbook.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IngredientDAO {

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public Optional<Ingredient> findById(int id) {
        return ingredientRepository.findById(id);
    }

    public Ingredient findByName(String name) {
        return ingredientRepository.findByName(name);
    }

    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public void deleteById(int id) {
        ingredientRepository.deleteById(id);
    }
}

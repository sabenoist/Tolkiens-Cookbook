package com.sander.tolkiens_cookbook.repository;

import com.sander.tolkiens_cookbook.exception.ResourceNotFoundException;
import com.sander.tolkiens_cookbook.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class IngredientDAO {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public Optional<Ingredient> findById(int id) {
        return ingredientRepository.findById(id);
    }

    public Ingredient findByName(String name) {
        return ingredientRepository.findByName(name);
    }

    public boolean existsByName(String name) {
        return ingredientRepository.existsByName(name);
    }

    @Transactional
    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Transactional
    public void deleteById(int id) {
        recipeIngredientRepository.deleteByIngredientId(id);
        ingredientRepository.deleteById(id);
    }

    @Transactional
    public Ingredient update(int id, Ingredient updatedIngredient) {
        // Find existing ingredient
        Ingredient existingIngredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with ID: " + id));

        // Update fields
        existingIngredient.setName(updatedIngredient.getName());
        existingIngredient.setCategory(updatedIngredient.getCategory());

        // Return updated (managed) entity
        return existingIngredient;
    }
}

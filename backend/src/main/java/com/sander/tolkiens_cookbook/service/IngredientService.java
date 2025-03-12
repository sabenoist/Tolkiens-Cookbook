package com.sander.tolkiens_cookbook.service;

import com.sander.tolkiens_cookbook.exception.ResourceNotFoundException;
import com.sander.tolkiens_cookbook.repository.IngredientDAO;
import com.sander.tolkiens_cookbook.dto.IngredientDTO;
import com.sander.tolkiens_cookbook.mapper.IngredientMapper;
import com.sander.tolkiens_cookbook.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    @Autowired
    private IngredientDAO ingredientDAO;

    public List<IngredientDTO> getAllIngredients() {
        return ingredientDAO.findAll().stream()
                .map(IngredientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public IngredientDTO getIngredientById(int id) {
        Optional<Ingredient> ingredientOpt = ingredientDAO.findById(id);
        return ingredientOpt.map(IngredientMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient with ID " + id + " not found"));
    }

    @Transactional
    public Ingredient createIngredient(Ingredient ingredient) {
        //Check if ingredient already exists to avoid duplicates
        if (ingredientDAO.existsByName(ingredient.getName())) {
            throw new IllegalArgumentException("Ingredient with name '" + ingredient.getName() + "' already exists.");
        }

        return ingredientDAO.save(ingredient);
    }

    public Ingredient getIngredientByName(String name) {
        return ingredientDAO.findByName(name);
    }

    public void deleteIngredient(int id) {
        ingredientDAO.deleteById(id);
    }

    @Transactional
    public Ingredient updateIngredient(int id, Ingredient ingredient) {
        return ingredientDAO.update(id, ingredient);
    }
}

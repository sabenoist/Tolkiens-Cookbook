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

/**
 * Service class for managing {@link Ingredient} entities.
 *
 * Provides business logic for creating, retrieving, updating, and deleting ingredients.
 */
@Service
public class IngredientService {

    @Autowired
    private IngredientDAO ingredientDAO;

    /**
     * Retrieves all ingredients as DTOs.
     *
     * @return List of {@link IngredientDTO} representing all ingredients in the system.
     */
    public List<IngredientDTO> getAllIngredients() {
        return ingredientDAO.findAll().stream()
                .map(IngredientMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an ingredient by its ID.
     *
     * @param id The ID of the ingredient to retrieve.
     * @return {@link IngredientDTO} representing the ingredient.
     * @throws ResourceNotFoundException if the ingredient with the specified ID is not found.
     */
    public IngredientDTO getIngredientById(int id) {
        Optional<Ingredient> ingredientOpt = ingredientDAO.findById(id);
        return ingredientOpt.map(IngredientMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient with ID " + id + " not found"));
    }

    /**
     * Creates a new ingredient.
     *
     * @param ingredient The {@link Ingredient} entity to create.
     * @return The created {@link Ingredient} entity.
     * @throws IllegalArgumentException if an ingredient with the same name already exists.
     */
    @Transactional
    public Ingredient createIngredient(Ingredient ingredient) {
        // Check if ingredient already exists to avoid duplicates
        if (ingredientDAO.existsByName(ingredient.getName())) {
            throw new IllegalArgumentException("Ingredient with name '" + ingredient.getName() + "' already exists.");
        }

        return ingredientDAO.save(ingredient);
    }

    /**
     * Retrieves an ingredient by its name.
     *
     * @param name The name of the ingredient to find.
     * @return The matching {@link Ingredient}, or null if not found.
     */
    public Ingredient getIngredientByName(String name) {
        return ingredientDAO.findByName(name);
    }

    /**
     * Deletes an ingredient by its ID.
     * Also removes any references in recipe-ingredient combinations.
     *
     * @param id The ID of the ingredient to delete.
     */
    public void deleteIngredient(int id) {
        ingredientDAO.deleteById(id);
    }

    /**
     * Updates an existing ingredient by its ID.
     *
     * @param id         The ID of the ingredient to update.
     * @param ingredient The updated {@link Ingredient} entity with new values.
     * @return The updated {@link Ingredient} entity.
     * @throws ResourceNotFoundException if the ingredient to update is not found.
     */
    @Transactional
    public Ingredient updateIngredient(int id, Ingredient ingredient) {
        return ingredientDAO.update(id, ingredient);
    }
}

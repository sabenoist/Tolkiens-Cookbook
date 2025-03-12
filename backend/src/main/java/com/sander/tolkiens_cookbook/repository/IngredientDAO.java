package com.sander.tolkiens_cookbook.repository;

import com.sander.tolkiens_cookbook.exception.ResourceNotFoundException;
import com.sander.tolkiens_cookbook.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) for managing {@link Ingredient} entities.
 * <p>
 * Provides methods for common CRUD operations such as finding, saving, updating, and deleting ingredients.
 * This class also ensures that any associated {@link com.sander.tolkiens_cookbook.model.RecipeIngredient}
 * relationships are properly handled when deleting an ingredient.
 * </p>
 */
@Repository
public class IngredientDAO {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    /**
     * Retrieves all ingredients from the database.
     *
     * @return a list of all {@link Ingredient} entities.
     */
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    /**
     * Retrieves an ingredient by its ID.
     *
     * @param id the ID of the ingredient to find.
     * @return an {@link Optional} containing the ingredient if found, or empty if not.
     */
    public Optional<Ingredient> findById(int id) {
        return ingredientRepository.findById(id);
    }

    /**
     * Retrieves an ingredient by its name.
     *
     * @param name the name of the ingredient.
     * @return the {@link Ingredient} with the specified name.
     */
    public Ingredient findByName(String name) {
        return ingredientRepository.findByName(name);
    }

    /**
     * Checks if an ingredient with the specified name exists in the database.
     *
     * @param name the name to check for existence.
     * @return {@code true} if an ingredient with that name exists, {@code false} otherwise.
     */
    public boolean existsByName(String name) {
        return ingredientRepository.existsByName(name);
    }

    /**
     * Saves a new or existing ingredient to the database.
     *
     * @param ingredient the ingredient to save.
     * @return the saved {@link Ingredient} entity.
     */
    @Transactional
    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    /**
     * Deletes an ingredient by its ID. Also removes all associated
     * {@link com.sander.tolkiens_cookbook.model.RecipeIngredient} records to maintain referential integrity.
     *
     * @param id the ID of the ingredient to delete.
     */
    @Transactional
    public void deleteById(int id) {
        recipeIngredientRepository.deleteByIngredientId(id); // Clean up relations first
        ingredientRepository.deleteById(id);
    }

    /**
     * Updates an existing ingredient's information in the database.
     *
     * @param id               the ID of the ingredient to update.
     * @param updatedIngredient an {@link Ingredient} object containing updated fields.
     * @return the updated (managed) {@link Ingredient} entity.
     * @throws ResourceNotFoundException if no ingredient is found with the specified ID.
     */
    @Transactional
    public Ingredient update(int id, Ingredient updatedIngredient) {
        // Find existing ingredient
        Ingredient existingIngredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with ID: " + id));

        // Update fields
        existingIngredient.setName(updatedIngredient.getName());
        existingIngredient.setCategory(updatedIngredient.getCategory());

        // Return updated entity
        return existingIngredient;
    }
}
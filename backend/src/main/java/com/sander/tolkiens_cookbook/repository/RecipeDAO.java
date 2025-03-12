package com.sander.tolkiens_cookbook.repository;

import com.sander.tolkiens_cookbook.exception.ResourceNotFoundException;
import com.sander.tolkiens_cookbook.model.Ingredient;
import com.sander.tolkiens_cookbook.model.Recipe;
import com.sander.tolkiens_cookbook.model.RecipeIngredient;
import com.sander.tolkiens_cookbook.model.RecipeIngredientId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Data Access Object (DAO) class for managing {@link Recipe} entities and their relationships.
 * <p>
 * This class encapsulates all logic for retrieving, creating, updating, and deleting recipes,
 * including the handling of their related ingredients via {@link RecipeIngredient}.
 * </p>
 */
@Repository
public class RecipeDAO {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    /**
     * Retrieves all recipes from the database.
     *
     * @return a list of all {@link Recipe} entities.
     */
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    /**
     * Finds a recipe by its ID.
     *
     * @param id the ID of the recipe.
     * @return an {@link Optional} containing the recipe if found, or empty otherwise.
     */
    public Optional<Recipe> findById(int id) {
        return recipeRepository.findById(id);
    }

    /**
     * Searches for recipes based on ingredients, keywords, and inclusion/exclusion filters.
     *
     * @param includeIngredients list of ingredient names to include.
     * @param excludeIngredients list of ingredient names to exclude.
     * @param includeCount       number of included ingredients (for filtering).
     * @param keywordPattern     search pattern for instructions.
     * @param includeEmpty       whether the includeIngredients list is empty.
     * @param excludeEmpty       whether the excludeIngredients list is empty.
     * @return a list of matching {@link Recipe} entities.
     */
    public List<Recipe> searchRecipes(List<String> includeIngredients,
                                      List<String> excludeIngredients,
                                      long includeCount,
                                      String keywordPattern,
                                      boolean includeEmpty,
                                      boolean excludeEmpty) {
        return recipeRepository.searchRecipes(
                includeIngredients,
                excludeIngredients,
                includeCount,
                keywordPattern,
                includeEmpty,
                excludeEmpty
        );
    }

    /**
     * Saves a new recipe and its associated ingredients to the database.
     *
     * @param recipe the {@link Recipe} to save.
     * @return the saved {@link Recipe}, including its linked ingredients.
     */
    @Transactional
    public Recipe save(Recipe recipe) {
        List<RecipeIngredient> ingredients = recipe.getIngredients();
        recipe.setIngredients(null); // Detach ingredients temporarily

        Recipe savedRecipe = recipeRepository.save(recipe);

        List<RecipeIngredient> updatedIngredients = new ArrayList<>();

        // create RecipeIngredient associations in the database
        for (RecipeIngredient recipeIngredient : ingredients) {
            Ingredient attachedIngredient = ingredientRepository.findById(recipeIngredient.getIngredient().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient with ID " + recipeIngredient.getIngredient().getId() + " not found"));

            RecipeIngredientId id = new RecipeIngredientId(savedRecipe.getId(), attachedIngredient.getId());

            recipeIngredient.setId(id);
            recipeIngredient.setRecipe(savedRecipe);
            recipeIngredient.setIngredient(attachedIngredient);
            recipeIngredient.setQuantity(recipeIngredient.getQuantity());

            updatedIngredients.add(recipeIngredient);
        }

        recipeIngredientRepository.saveAll(updatedIngredients);
        savedRecipe.setIngredients(updatedIngredients);

        return savedRecipe;
    }

    /**
     * Updates an existing recipe and its associated ingredients in the database.
     *
     * @param recipeId       the ID of the recipe to update.
     * @param updatedRecipe  the updated {@link Recipe} data.
     * @return the updated {@link Recipe} entity.
     */
    @Transactional
    public Recipe update(int recipeId, Recipe updatedRecipe) {
        Recipe existingRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with ID: " + recipeId));

        // update recipe fields except ingredients
        existingRecipe.setName(updatedRecipe.getName());
        existingRecipe.setServings(updatedRecipe.getServings());
        existingRecipe.setInstructions(updatedRecipe.getInstructions());

        existingRecipe.setIngredients(new ArrayList<>()); // Detach for now

        recipeIngredientRepository.deleteByRecipeId(recipeId); // Clear old ingredients

        List<RecipeIngredient> updatedIngredients = new ArrayList<>();

        // update the recipe ingredients list
        for (RecipeIngredient incomingIngredient : updatedRecipe.getIngredients()) {
            Ingredient attachedIngredient = ingredientRepository.findById(incomingIngredient.getIngredient().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient with ID " + incomingIngredient.getIngredient().getId() + " not found"));

            RecipeIngredientId id = new RecipeIngredientId(recipeId, attachedIngredient.getId());

            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setId(id);
            recipeIngredient.setRecipe(existingRecipe);
            recipeIngredient.setIngredient(attachedIngredient);
            recipeIngredient.setQuantity(incomingIngredient.getQuantity());

            updatedIngredients.add(recipeIngredient);
        }

        recipeIngredientRepository.saveAll(updatedIngredients);
        existingRecipe.setIngredients(updatedIngredients);

        return existingRecipe;
    }

    /**
     * Deletes a recipe and all its ingredient links from the database.
     *
     * @param id the ID of the recipe to delete.
     */
    @Transactional
    public void deleteById(int id) {
        recipeIngredientRepository.deleteByRecipeId(id);
        recipeRepository.deleteById(id);
    }
}
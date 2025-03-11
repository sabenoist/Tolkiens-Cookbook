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

@Repository
public class RecipeDAO {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> findById(int id) {
        return recipeRepository.findById(id);
    }

    public List<Recipe> searchRecipes(List<String> includeIngredients, List<String> excludeIngredients, String keyword) {
        long includeCount = (includeIngredients != null) ? includeIngredients.size() : 0;

        // Formatting the keyword pattern for SQL LIKE query
        String keywordPattern = (keyword != null && !keyword.isEmpty()) ? "%" + keyword.toLowerCase() + "%" : null;

        return recipeRepository.searchRecipes(includeIngredients, excludeIngredients, includeCount, keywordPattern);
    }

    @Transactional
    public Recipe save(Recipe recipe) {
        // Temporarily store RecipeIngredients
        List<RecipeIngredient> ingredients = recipe.getIngredients(); // Now as List

        // Save Recipe only (without ingredients)
        recipe.setIngredients(null); // Detach ingredients temporarily
        Recipe savedRecipe = recipeRepository.save(recipe);

        // Prepare list for updated RecipeIngredients
        List<RecipeIngredient> updatedIngredients = new ArrayList<>();

        // Process and link each ingredient
        for (RecipeIngredient recipeIngredient : ingredients) {
            // Fetch managed Ingredient entity
            Ingredient attachedIngredient = ingredientRepository.findById(recipeIngredient.getIngredient().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient with ID " + recipeIngredient.getIngredient().getId() + " not found"));

            // Create composite key
            RecipeIngredientId id = new RecipeIngredientId();
            id.setRecipeId(savedRecipe.getId());
            id.setIngredientId(attachedIngredient.getId());

            // Set fields for RecipeIngredient
            recipeIngredient.setId(id);
            recipeIngredient.setRecipe(savedRecipe); // Link recipe
            recipeIngredient.setIngredient(attachedIngredient); // Link ingredient
            recipeIngredient.setQuantity(recipeIngredient.getQuantity()); // Quantity from input

            updatedIngredients.add(recipeIngredient);
        }

        // Save all RecipeIngredient links in the DB
        recipeIngredientRepository.saveAll(updatedIngredients);

        // Reattach to saved Recipe
        savedRecipe.setIngredients(updatedIngredients);

        return savedRecipe;
    }

    @Transactional
    public Recipe update(int recipeId, Recipe updatedRecipe) {
        Recipe existingRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with ID: " + recipeId));

        existingRecipe.setName(updatedRecipe.getName());
        existingRecipe.setServings(updatedRecipe.getServings());
        existingRecipe.setInstructions(updatedRecipe.getInstructions());

        existingRecipe.setIngredients(new ArrayList<>()); // Detach for now

        recipeIngredientRepository.deleteByRecipeId(recipeId); // Manual delete

        List<RecipeIngredient> updatedIngredients = new ArrayList<>();

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

        existingRecipe.setIngredients(updatedIngredients); // Reattach for response

        return existingRecipe;
    }

    @Transactional
    public void deleteById(int id) {
        recipeIngredientRepository.deleteByRecipeId(id);
        recipeRepository.deleteById(id);
    }
}

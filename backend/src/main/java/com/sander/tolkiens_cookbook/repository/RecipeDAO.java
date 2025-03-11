package com.sander.tolkiens_cookbook.repository;

import com.sander.tolkiens_cookbook.model.Ingredient;
import com.sander.tolkiens_cookbook.model.Recipe;
import com.sander.tolkiens_cookbook.model.RecipeIngredient;
import com.sander.tolkiens_cookbook.model.RecipeIngredientId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        Set<RecipeIngredient> ingredients = recipe.getIngredients();

        // Save Recipe only
        recipe.setIngredients(null);
        Recipe savedRecipe = recipeRepository.save(recipe);

        // Add RecipeIngredient combinations to the database
        Set<RecipeIngredient> updatedIngredients = new HashSet<>();
        for (RecipeIngredient recipeIngredient : ingredients) {
            // Fetch managed Ingredient
            Ingredient attachedIngredient = ingredientRepository.findById(recipeIngredient.getIngredient().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Ingredient with ID " + recipeIngredient.getIngredient().getId() + " not found"));

            // Create composite key
            RecipeIngredientId id = new RecipeIngredientId();
            id.setRecipeId(savedRecipe.getId());
            id.setIngredientId(attachedIngredient.getId());

            // Set fields
            recipeIngredient.setId(id);
            recipeIngredient.setRecipe(savedRecipe);
            recipeIngredient.setIngredient(attachedIngredient);
            recipeIngredient.setQuantity(recipeIngredient.getQuantity());

            updatedIngredients.add(recipeIngredient);
        }

        // Save RecipeIngredients
        recipeIngredientRepository.saveAll(updatedIngredients);

        // Set ingredients back to Recipe
        savedRecipe.setIngredients(updatedIngredients);

        return savedRecipe;
    }

    @Transactional
    public void deleteById(int id) {
        recipeIngredientRepository.deleteByRecipeId(id);
        recipeRepository.deleteById(id);
    }
}

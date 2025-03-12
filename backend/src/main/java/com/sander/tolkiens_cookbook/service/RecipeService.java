package com.sander.tolkiens_cookbook.service;

import com.sander.tolkiens_cookbook.exception.ResourceNotFoundException;
import com.sander.tolkiens_cookbook.mapper.RecipeMapper;
import com.sander.tolkiens_cookbook.model.RecipeIngredient;
import com.sander.tolkiens_cookbook.model.RecipeIngredientId;
import com.sander.tolkiens_cookbook.repository.RecipeDAO;
import com.sander.tolkiens_cookbook.dto.RecipeDTO;
import com.sander.tolkiens_cookbook.model.Recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing {@link Recipe} entities.
 * Provides business logic for creating, retrieving, updating, deleting, and searching recipes.
 */
@Service
public class RecipeService {

    @Autowired
    private RecipeDAO recipeDAO;

    /**
     * Retrieves all recipes and maps them to DTOs.
     *
     * @return List of {@link RecipeDTO} representing all recipes in the system.
     */
    public List<RecipeDTO> getAllRecipes() {
        List<Recipe> recipes = recipeDAO.findAll();

        return recipes.stream()
                .map(RecipeMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a recipe by its ID.
     *
     * @param id The ID of the recipe to retrieve.
     * @return {@link RecipeDTO} representing the recipe.
     * @throws ResourceNotFoundException if the recipe with the specified ID is not found.
     */
    public RecipeDTO getRecipeById(int id) {
        return recipeDAO.findById(id)
                .map(RecipeMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe with ID " + id + " not found"));
    }

    public List<RecipeDTO> searchRecipes(List<String> includeIngredients,
                                         List<String> excludeIngredients,
                                         String keyword,
                                         Integer servings,  // added servings
                                         Boolean isVegetarian) {

        // Handle null or empty lists
        if (includeIngredients == null) includeIngredients = Collections.emptyList();
        if (excludeIngredients == null) excludeIngredients = Collections.emptyList();

        long includeCount = includeIngredients.size();
        String keywordPattern = (keyword == null || keyword.isEmpty()) ? null : "%" + keyword + "%";

        boolean includeEmpty = includeIngredients.isEmpty();
        boolean excludeEmpty = excludeIngredients.isEmpty();

        List<Recipe> recipes = recipeDAO.searchRecipes(
                includeIngredients,
                excludeIngredients,
                includeCount,
                keywordPattern,
                includeEmpty,
                excludeEmpty,
                servings
        );

        // Vegetarian filtering (still in-memory if needed)
        return recipes.stream()
                .filter(recipe -> {
                    if (isVegetarian == null) return true;
                    return isVegetarian == recipe.isVegetarian();
                })
                .map(RecipeMapper::toDTO)
                .collect(Collectors.toList());
    }


    /**
     * Creates a new recipe along with its recipe-ingredient relationships.
     *
     * @param recipe The {@link Recipe} entity to create.
     * @return {@link RecipeDTO} representing the created recipe.
     */
    @Transactional
    public RecipeDTO save(Recipe recipe) {
        // Prepare composite IDs for RecipeIngredients
        for (RecipeIngredient recipeIngredient : recipe.getIngredients()) {
            RecipeIngredientId id = new RecipeIngredientId();
            id.setRecipeId(recipe.getId());
            id.setIngredientId(recipeIngredient.getIngredient().getId());
            recipeIngredient.setId(id);
            recipeIngredient.setRecipe(recipe);
        }

        return RecipeMapper.toDTO(recipeDAO.save(recipe));
    }

    /**
     * Updates an existing recipe by its ID.
     *
     * @param id            The ID of the recipe to update.
     * @param updatedRecipe The updated {@link Recipe} entity with new values.
     * @return {@link RecipeDTO} representing the updated recipe.
     */
    @Transactional
    public RecipeDTO updateRecipe(int id, Recipe updatedRecipe) {
        return RecipeMapper.toDTO(recipeDAO.update(id, updatedRecipe));
    }

    /**
     * Deletes a recipe by its ID, along with its associated recipe-ingredient links.
     *
     * @param id The ID of the recipe to delete.
     */
    @Transactional
    public void deleteRecipe(int id) {
        recipeDAO.deleteById(id);
    }
}

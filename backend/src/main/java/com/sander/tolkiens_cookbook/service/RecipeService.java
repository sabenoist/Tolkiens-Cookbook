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

@Service
public class RecipeService {
    @Autowired
    private RecipeDAO recipeDAO;

    public List<RecipeDTO> getAllRecipes() {
        List<Recipe> recipes = recipeDAO.findAll();

        return recipes.stream()
                .map(RecipeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RecipeDTO getRecipeById(int id) {
        return recipeDAO.findById(id)
                .map(RecipeMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe with ID " + id + " not found"));
    }

    public List<RecipeDTO> searchRecipes(List<String> includeIngredients,
                                         List<String> excludeIngredients,
                                         String keyword,
                                         Boolean isVegetarian) {

        // Handle null or empty lists
        if (includeIngredients == null) includeIngredients = Collections.emptyList();
        if (excludeIngredients == null) excludeIngredients = Collections.emptyList();

        long includeCount = includeIngredients.size();
        String keywordPattern = (keyword == null || keyword.isEmpty()) ? null : "%" + keyword + "%";

        boolean includeEmpty = includeIngredients.isEmpty();
        boolean excludeEmpty = excludeIngredients.isEmpty();

        // Perform query
        List<Recipe> recipes = recipeDAO.searchRecipes(
                includeIngredients,
                excludeIngredients,
                includeCount,
                keywordPattern,
                includeEmpty,
                excludeEmpty
        );

        // filter vegetarian
        return recipes.stream()
                .filter(recipe -> {
                    if (isVegetarian == null) return true; // No filter applied
                    if (isVegetarian) return recipe.isVegetarian(); // Only vegetarian
                    return !recipe.isVegetarian(); // Only non-vegetarian
                })
                .map(RecipeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public RecipeDTO save(Recipe recipe) {
        for (RecipeIngredient recipeIngredient : recipe.getIngredients()) {
            RecipeIngredientId id = new RecipeIngredientId();
            id.setRecipeId(recipe.getId());  // Make sure Recipe has an ID first!
            id.setIngredientId(recipeIngredient.getIngredient().getId());
            recipeIngredient.setId(id);
            recipeIngredient.setRecipe(recipe);
        }

        return RecipeMapper.toDTO(recipeDAO.save(recipe));
    }

    @Transactional
    public RecipeDTO updateRecipe(int id, Recipe updatedRecipe) {
        return RecipeMapper.toDTO(recipeDAO.update(id, updatedRecipe));
    }

    @Transactional
    public void deleteRecipe(int id) {
        recipeDAO.deleteById(id);
    }
}

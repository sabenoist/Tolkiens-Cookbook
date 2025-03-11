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
        return recipeDAO.searchRecipes(includeIngredients, excludeIngredients, keyword).stream()
                .filter(recipe -> isVegetarian == null || !isVegetarian || recipe.isVegetarian())
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

    public void deleteRecipe(int id) {
        recipeDAO.deleteById(id);
    }
}

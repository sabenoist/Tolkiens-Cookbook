package com.sander.tolkiens_cookbook.repository;

import com.sander.tolkiens_cookbook.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RecipeDAO {
    @Autowired
    private RecipeRepository recipeRepository;

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

    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public void deleteById(int id) {
        recipeRepository.deleteById(id);
    }
}

package com.sander.tolkiens_cookbook.service;

import com.sander.tolkiens_cookbook.mapper.RecipeMapper;
import com.sander.tolkiens_cookbook.repository.RecipeDAO;
import com.sander.tolkiens_cookbook.dto.RecipeDTO;
import com.sander.tolkiens_cookbook.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
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
                .orElse(null); // ToDo: throw an exception if not found
    }

    public List<RecipeDTO> searchRecipes(List<String> includeIngredients,
                                         List<String> excludeIngredients,
                                         String keyword) {
        List<Recipe> recipes = recipeDAO.searchRecipes(includeIngredients, excludeIngredients, keyword);

        return recipes.stream()
                .map(recipe -> new RecipeDTO(
                        recipe.getId(),
                        recipe.getName(),
                        recipe.getServings(),
                        recipe.isVegetarian(),
                        recipe.getRecipeIngredientsDTO(),
                        recipe.getInstructions()))
                .collect(Collectors.toList());
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeDAO.save(recipe);
    }

    public void deleteRecipe(int id) {
        recipeDAO.deleteById(id);
    }
}

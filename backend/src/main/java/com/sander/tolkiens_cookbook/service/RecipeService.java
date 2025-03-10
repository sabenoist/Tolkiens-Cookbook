package com.sander.tolkiens_cookbook.service;

import com.sander.tolkiens_cookbook.repository.RecipeDAO;
import com.sander.tolkiens_cookbook.dto.RecipeDTO;
import com.sander.tolkiens_cookbook.dto.RecipeIngredientDTO;
import com.sander.tolkiens_cookbook.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    @Autowired
    private RecipeDAO recipeDAO;  // Use DAO instead of Repository

    public List<RecipeDTO> getAllRecipes() {
        List<Recipe> recipes = recipeDAO.findAll();
        return recipes.stream().map(recipe -> {
            List<RecipeIngredientDTO> recipeIngredients = recipe.getIngredients().stream()
                    .map(recipeIngredient -> new RecipeIngredientDTO(
                            recipeIngredient.getQuantity(),
                            recipeIngredient.getIngredient().getName()
                    ))
                    .collect(Collectors.toList());

            boolean isVegetarian = recipe.getIngredients().stream()
                    .allMatch(recipeIngredient -> recipeIngredient.getIngredient().isVegetarian());

            return new RecipeDTO(
                    recipe.getId(),
                    recipe.getName(),
                    recipe.getServings(),
                    isVegetarian,
                    recipeIngredients
            );
        }).collect(Collectors.toList());
    }

    public RecipeDTO getRecipeById(int id) {
        return recipeDAO.findById(id).map(recipe -> {
            List<RecipeIngredientDTO> recipeIngredients = recipe.getIngredients().stream()
                    .map(recipeIngredient -> new RecipeIngredientDTO(
                            recipeIngredient.getQuantity(),
                            recipeIngredient.getIngredient().getName()
                    ))
                    .collect(Collectors.toList());

            boolean isVegetarian = recipe.getIngredients().stream()
                    .allMatch(recipeIngredient -> recipeIngredient.getIngredient().isVegetarian());

            return new RecipeDTO(
                    recipe.getId(),
                    recipe.getName(),
                    recipe.getServings(),
                    isVegetarian,
                    recipeIngredients
            );
        }).orElse(null); // ToDO: throw an exception if not found
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeDAO.save(recipe);
    }

    public void deleteRecipe(int id) {
        recipeDAO.deleteById(id);
    }
}

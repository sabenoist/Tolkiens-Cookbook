package com.sander.tolkiens_cookbook.service;

import com.sander.tolkiens_cookbook.dto.RecipeDTO;
import com.sander.tolkiens_cookbook.dto.RecipeIngredientDTO;
import com.sander.tolkiens_cookbook.entity.Recipe;
import com.sander.tolkiens_cookbook.repository.RecipeIngredientRepository;
import com.sander.tolkiens_cookbook.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    public List<RecipeDTO> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream().map(recipe -> {
            // Map each Recipe entity to a RecipeDTO
            List<RecipeIngredientDTO> recipeIngredients = recipe.getIngredients().stream()
                    .map(recipeIngredient -> new RecipeIngredientDTO(
                            recipeIngredient.getQuantity(),
                            recipeIngredient.getIngredient().getName()
                    ))
                    .collect(Collectors.toList());

            return new RecipeDTO(
                    recipe.getId(),
                    recipe.getName(),
                    recipe.getServings(),
                    recipeIngredients
            );
        }).collect(Collectors.toList());
    }

    public RecipeDTO getRecipeById(int id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe != null) {
            // Map the RecipeIngredient objects to RecipeIngredientDTOs
            List<RecipeIngredientDTO> recipeIngredients = recipe.getIngredients().stream()
                    .map(recipeIngredient -> new RecipeIngredientDTO(
                            recipeIngredient.getQuantity(),
                            recipeIngredient.getIngredient().getName()
                    ))
                    .collect(Collectors.toList());

            return new RecipeDTO(
                    recipe.getId(),
                    recipe.getName(),
                    recipe.getServings(),
                    recipeIngredients
            );
        }
        return null; // ToDO: throw an exception if not found
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(int  id) {
        recipeRepository.deleteById(id);
    }
}
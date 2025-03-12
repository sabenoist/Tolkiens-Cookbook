package com.sander.tolkiens_cookbook.rest;

import com.sander.tolkiens_cookbook.dto.RecipeDTO;
import com.sander.tolkiens_cookbook.model.Recipe;
import com.sander.tolkiens_cookbook.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * REST controller for managing recipes.
 * Provides endpoints for creating, reading, updating, deleting, and filtering recipes.
 */
@RestController
@RequestMapping("/api/recipes")
public class RecipeRestController {

    @Autowired
    private RecipeService recipeService;

    /**
     * Searches for recipes based on included and excluded ingredients, a keyword, servings, and vegetarian status.
     *
     * @param includeIngredients Comma-separated list of ingredient names to include (optional).
     * @param excludeIngredients Comma-separated list of ingredient names to exclude (optional).
     * @param keyword            Keyword to search for in recipe instructions (optional).
     * @param servings           Exact servings to filter (optional).
     * @param isVegetarian       Boolean indicating if only vegetarian recipes should be returned (optional).
     * @return List of {@link RecipeDTO} matching the search criteria.
     */
    @GetMapping("/filter")
    public List<RecipeDTO> searchRecipes(
            @RequestParam(required = false) String includeIngredients,
            @RequestParam(required = false) String excludeIngredients,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer servings, // Added
            @RequestParam(required = false) Boolean isVegetarian) {

        List<String> includeList = (includeIngredients != null && !includeIngredients.isEmpty())
                ? Arrays.asList(includeIngredients.split(","))
                : Collections.emptyList();

        List<String> excludeList = (excludeIngredients != null && !excludeIngredients.isEmpty())
                ? Arrays.asList(excludeIngredients.split(","))
                : Collections.emptyList();

        return recipeService.searchRecipes(includeList, excludeList, keyword, servings, isVegetarian);
    }

    /**
     * Retrieves all recipes.
     *
     * @return List of all {@link RecipeDTO} in the database.
     */
    @GetMapping
    public List<RecipeDTO> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    /**
     * Retrieves a specific recipe by its ID.
     *
     * @param id The ID of the recipe to retrieve.
     * @return The corresponding {@link RecipeDTO}.
     */
    @GetMapping("/{id}")
    public RecipeDTO getRecipeById(@PathVariable int id) {
        return recipeService.getRecipeById(id);
    }

    /**
     * Creates a new recipe and stores it in the database.
     *
     * @param recipe The {@link Recipe} object containing recipe details.
     * @return The newly created {@link RecipeDTO}.
     */
    @PostMapping
    public RecipeDTO createRecipe(@RequestBody Recipe recipe) {
        return recipeService.save(recipe);
    }

    /**
     * Updates an existing recipe based on its ID.
     *
     * @param id            The ID of the recipe to update.
     * @param updatedRecipe The updated {@link Recipe} data.
     * @return The updated {@link RecipeDTO}.
     */
    @PutMapping("/{id}")
    public RecipeDTO updateRecipe(@PathVariable int id, @RequestBody Recipe updatedRecipe) {
        return recipeService.updateRecipe(id, updatedRecipe);
    }

    /**
     * Deletes a recipe based on its ID.
     *
     * @param id The ID of the recipe to delete.
     * @return ResponseEntity with HTTP status 204 (No Content) if deletion is successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}

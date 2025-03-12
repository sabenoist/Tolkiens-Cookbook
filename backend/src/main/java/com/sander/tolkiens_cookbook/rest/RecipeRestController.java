package com.sander.tolkiens_cookbook.rest;

import com.sander.tolkiens_cookbook.dto.RecipeDTO;
import com.sander.tolkiens_cookbook.model.Recipe;
import com.sander.tolkiens_cookbook.model.RecipeIngredient;
import com.sander.tolkiens_cookbook.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeRestController {
    private final RecipeService recipeService;

    public RecipeRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/filter")
    public List<RecipeDTO> searchRecipes(
            @RequestParam(required = false) String includeIngredients,
            @RequestParam(required = false) String excludeIngredients,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean isVegetarian) {

        // Split comma-separated lists
        List<String> includeList = (includeIngredients != null && !includeIngredients.isEmpty())
                ? Arrays.asList(includeIngredients.split(","))
                : Collections.emptyList();

        List<String> excludeList = (excludeIngredients != null && !excludeIngredients.isEmpty())
                ? Arrays.asList(excludeIngredients.split(","))
                : Collections.emptyList();

        return recipeService.searchRecipes(includeList, excludeList, keyword, isVegetarian);
    }

    @GetMapping
    public List<RecipeDTO> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public RecipeDTO getRecipeById(@PathVariable int id) {
        return recipeService.getRecipeById(id);
    }

    @PostMapping
    public RecipeDTO createRecipe(@RequestBody Recipe recipe) {
        return recipeService.save(recipe);
    }

    @PutMapping("/{id}")
    public RecipeDTO updateRecipe(@PathVariable int id, @RequestBody Recipe updatedRecipe) {
        return recipeService.updateRecipe(id, updatedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
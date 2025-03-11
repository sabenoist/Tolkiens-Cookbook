package com.sander.tolkiens_cookbook.rest;

import com.sander.tolkiens_cookbook.dto.RecipeDTO;
import com.sander.tolkiens_cookbook.model.Recipe;
import com.sander.tolkiens_cookbook.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeRestController {
    private final RecipeService recipeService;

    public RecipeRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<RecipeDTO> searchRecipes(
            @RequestParam(required = false) String includeIngredients,
            @RequestParam(required = false) String excludeIngredients,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String isVegetarian) {

        List<String> includeList = (includeIngredients != null && !includeIngredients.isEmpty())
                ? Arrays.asList(includeIngredients.split(","))
                : null;

        List<String> excludeList = (excludeIngredients != null && !excludeIngredients.isEmpty())
                ? Arrays.asList(excludeIngredients.split(","))
                : null;

        Boolean vegetarian = null;
        if (isVegetarian != null) {
            vegetarian = Boolean.parseBoolean(isVegetarian);
        }

        return recipeService.searchRecipes(includeList, excludeList, keyword, vegetarian);
    }
}
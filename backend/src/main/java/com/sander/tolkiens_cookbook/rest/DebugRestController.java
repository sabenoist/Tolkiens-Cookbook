package com.sander.tolkiens_cookbook.rest;

import com.sander.tolkiens_cookbook.model.Recipe;
import com.sander.tolkiens_cookbook.model.RecipeIngredient;
import com.sander.tolkiens_cookbook.service.IngredientService;
import com.sander.tolkiens_cookbook.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/debug")
public class DebugRestController {
    @Autowired
    private RecipeService recipeService;

    @Autowired
    private IngredientService ingredientService;

    @PostMapping("/test-recipe-body")
    public String testRecipeBody(@RequestBody Recipe recipe) {
        System.out.println("Received Recipe: " + recipe.getName());
        for (RecipeIngredient ingredient : recipe.getIngredients()) {
            System.out.println("Ingredient ID: " +
                    (ingredient.getIngredient() != null ? ingredient.getIngredient().getId() : "NULL") +
                    ", Quantity: " + ingredient.getQuantity());
        }
        return "Recipe received successfully!";
    }
}

package com.sander.tolkiens_cookbook.rest;

import com.sander.tolkiens_cookbook.service.IngredientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class DebugRestController {

    private final IngredientService ingredientService;

    public DebugRestController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/clear-ingredients")
    public String clearIngredients() {
        ingredientService.resetIngredients();
        return "Ingredients cleared!";
    }
}

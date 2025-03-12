package com.sander.tolkiens_cookbook.rest;

import com.sander.tolkiens_cookbook.dto.IngredientDTO;
import com.sander.tolkiens_cookbook.exception.ResourceNotFoundException;
import com.sander.tolkiens_cookbook.model.Ingredient;
import com.sander.tolkiens_cookbook.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientRestController {
    private final IngredientService ingredientService;

    public IngredientRestController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public List<IngredientDTO> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/{id}")
    public IngredientDTO getIngredient(@PathVariable int id) {
        return ingredientService.getIngredientById(id);
    }

    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        Ingredient createdIngredient = ingredientService.createIngredient(ingredient);
        return new ResponseEntity<>(createdIngredient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable int id, @RequestBody Ingredient ingredient) {
        try {
            Ingredient updated = ingredientService.updateIngredient(id, ingredient);
            return ResponseEntity.ok(updated);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable int id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }
}

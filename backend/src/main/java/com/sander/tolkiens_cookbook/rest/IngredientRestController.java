package com.sander.tolkiens_cookbook.rest;

import com.sander.tolkiens_cookbook.dto.IngredientDTO;
import com.sander.tolkiens_cookbook.exception.ResourceNotFoundException;
import com.sander.tolkiens_cookbook.model.Ingredient;
import com.sander.tolkiens_cookbook.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing ingredients.
 * <p>
 * Provides endpoints to create, read, update, and delete ingredients in the database.
 */
@RestController
@RequestMapping("/api/ingredients")
public class IngredientRestController {

    @Autowired
    private IngredientService ingredientService;

    /**
     * Retrieves all available ingredients.
     *
     * @return list of {@link IngredientDTO} representing all ingredients.
     */
    @GetMapping
    public List<IngredientDTO> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    /**
     * Retrieves a specific ingredient by its ID.
     *
     * @param id the ID of the ingredient to retrieve.
     * @return the {@link IngredientDTO} representing the ingredient.
     */
    @GetMapping("/{id}")
    public IngredientDTO getIngredient(@PathVariable int id) {
        return ingredientService.getIngredientById(id);
    }

    /**
     * Creates a new ingredient in the database.
     *
     * @param ingredient the {@link Ingredient} object to be created.
     * @return the created {@link Ingredient} along with HTTP 201 (Created) status.
     */
    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        Ingredient createdIngredient = ingredientService.createIngredient(ingredient);
        return new ResponseEntity<>(createdIngredient, HttpStatus.CREATED);
    }

    /**
     * Updates an existing ingredient based on its ID.
     *
     * @param id         the ID of the ingredient to update.
     * @param ingredient the {@link Ingredient} object containing updated information.
     * @return HTTP 200 (OK) with the updated ingredient, or HTTP 404 (Not Found) if not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable int id, @RequestBody Ingredient ingredient) {
        try {
            Ingredient updated = ingredientService.updateIngredient(id, ingredient);
            return ResponseEntity.ok(updated);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes an ingredient by its ID.
     * Also removes its relations from recipes via recipe ingredients.
     *
     * @param id the ID of the ingredient to delete.
     * @return HTTP 204 (No Content) on successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable int id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }
}

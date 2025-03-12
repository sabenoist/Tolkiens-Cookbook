package com.sander.tolkiens_cookbook.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sander.tolkiens_cookbook.model.Category;
import com.sander.tolkiens_cookbook.model.Ingredient;
import com.sander.tolkiens_cookbook.model.Recipe;
import com.sander.tolkiens_cookbook.model.RecipeIngredient;
import com.sander.tolkiens_cookbook.repository.IngredientRepository;
import com.sander.tolkiens_cookbook.repository.RecipeIngredientRepository;
import com.sander.tolkiens_cookbook.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for {@link RecipeRestControllerTest}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class RecipeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @BeforeEach
    void setUp() {
        recipeIngredientRepository.deleteAll();
        recipeRepository.deleteAll();
        ingredientRepository.deleteAll();
    }

    /**
     * Integration test for retrieving all recipes when the database is empty.
     */
    @Test
    void testGetAllRecipes_Empty() throws Exception {
        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    /**
     * Integration test for creating a new recipe.
     */
    @Test
    void testCreateRecipe() throws Exception {
        Ingredient tomato = new Ingredient("Tomato", Category.VEGETABLE);
        ingredientRepository.save(tomato);

        Recipe recipe = new Recipe("Tomato Salad", 2);
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setIngredient(tomato);
        recipeIngredient.setQuantity("100g");

        recipe.setIngredients(List.of(recipeIngredient));
        recipe.setInstructions("Mix everything together.");

        mockMvc.perform(post("/api/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tomato Salad"))
                .andExpect(jsonPath("$.servings").value(2));
    }

    /**
     * Integration test for retrieving a recipe by id.
     */
    @Test
    void testGetRecipeById() throws Exception {
        Ingredient tomato = new Ingredient("Tomato", Category.VEGETABLE);
        ingredientRepository.save(tomato);

        Recipe recipe = new Recipe("Tomato Soup", 3);
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setIngredient(tomato);
        recipeIngredient.setQuantity("200g");

        recipe.setIngredients(List.of(recipeIngredient));
        recipe.setInstructions("Cook tomato and blend.");

        recipe = recipeRepository.save(recipe);

        mockMvc.perform(get("/api/recipes/" + recipe.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tomato Soup"))
                .andExpect(jsonPath("$.servings").value(3));
    }

    /**
     * Integration test for retrieving a recipe by an id that does not exist.
     */
    @Test
    void testGetRecipeById_NotFound() throws Exception {
        mockMvc.perform(get("/api/recipes/999"))
                .andExpect(status().isNotFound());
    }

    /**
     * Integration test for updating a recipe.
     */
    @Test
    void testUpdateRecipe() throws Exception {
        Ingredient tomato = new Ingredient("Tomato", Category.VEGETABLE);
        ingredientRepository.save(tomato);

        Recipe recipe = new Recipe("Tomato Juice", 1);
        recipe.setInstructions("Blend tomato.");
        recipe.setIngredients(List.of());

        recipe = recipeRepository.save(recipe);

        recipe.setName("Fresh Tomato Juice");
        recipe.setServings(2);

        mockMvc.perform(put("/api/recipes/" + recipe.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fresh Tomato Juice"))
                .andExpect(jsonPath("$.servings").value(2));
    }

    /**
     * Integration test for deleting a recipe.
     */
    @Test
    void testDeleteRecipe() throws Exception {
        // Given
        Recipe recipe = new Recipe("Pasta", 2);
        recipe.setInstructions("Boil pasta and add sauce.");
        recipeRepository.save(recipe);

        // When & Then
        mockMvc.perform(delete("/api/recipes/" + recipe.getId()))
                .andExpect(status().isNoContent());

        // Check that it is deleted
        mockMvc.perform(get("/api/recipes/" + recipe.getId()))
                .andExpect(status().isNotFound());
    }
}

package com.sander.tolkiens_cookbook.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sander.tolkiens_cookbook.model.Category;
import com.sander.tolkiens_cookbook.model.Ingredient;
import com.sander.tolkiens_cookbook.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for {@link IngredientRestController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class IngredientRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        ingredientRepository.deleteAll();
    }

    /**
     * Integration test for retrieving all ingredients.
     */
    @Test
    void testGetAllIngredients() throws Exception {
        Ingredient ing1 = new Ingredient("Tomato", Category.VEGETABLE);
        Ingredient ing2 = new Ingredient("Chicken", Category.MEAT);
        ingredientRepository.save(ing1);
        ingredientRepository.save(ing2);

        mockMvc.perform(get("/api/ingredients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].name", is("Tomato")))
                .andExpect(jsonPath("$[1].name", is("Chicken")));
    }

    /**
     * Integration test for retrieving an ingredient by id.
     */
    @Test
    void testGetIngredientById() throws Exception {
        Ingredient ingredient = new Ingredient("Milk", Category.DAIRY);
        Ingredient saved = ingredientRepository.save(ingredient);

        mockMvc.perform(get("/api/ingredients/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Milk")))
                .andExpect(jsonPath("$.category", is("DAIRY")));
    }

    /**
     * Integration test for attempting to retrieve an ingredient by id that does not exist.
     */
    @Test
    void testGetIngredientById_NotFound() throws Exception {
        mockMvc.perform(get("/api/ingredients/9999"))
                .andExpect(status().isNotFound());
    }

    /**
     * Integration test for creating an ingredient.
     */
    @Test
    void testCreateIngredient() throws Exception {
        Ingredient newIngredient = new Ingredient("Rice", Category.GRAIN);

        mockMvc.perform(post("/api/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newIngredient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Rice")))
                .andExpect(jsonPath("$.category", is("GRAIN")));
    }

    /**
     * Integration test creating an ingredient that already exists.
     */
    @Test
    void testCreateIngredient_AlreadyExists() throws Exception {
        Ingredient ingredient = new Ingredient("Cheese", Category.DAIRY);
        ingredientRepository.save(ingredient);

        mockMvc.perform(post("/api/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredient)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Ingredient with name 'Cheese' already exists.")));
    }

    /**
     * Integration test for updating an ingredient.
     */
    @Test
    void testUpdateIngredient() throws Exception {
        Ingredient ingredient = new Ingredient("Apple", Category.FRUIT);
        Ingredient saved = ingredientRepository.save(ingredient);

        Ingredient updatedIngredient = new Ingredient("Green Apple", Category.FRUIT);

        mockMvc.perform(put("/api/ingredients/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedIngredient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Green Apple")));
    }

    /**
     * Integration test for updating an ingredient that does not exist.
     */
    @Test
    void testUpdateIngredient_NotFound() throws Exception {
        Ingredient updatedIngredient = new Ingredient("Updated", Category.OTHER);

        mockMvc.perform(put("/api/ingredients/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedIngredient)))
                .andExpect(status().isNotFound());
    }

    /**
     * Integration test for deleting an ingredient.
     */
    @Test
    void testDeleteIngredient() throws Exception {
        Ingredient ingredient = new Ingredient("Butter", Category.DAIRY);
        Ingredient saved = ingredientRepository.save(ingredient);

        mockMvc.perform(delete("/api/ingredients/" + saved.getId()))
                .andExpect(status().isNoContent());

        assertFalse(ingredientRepository.findById(saved.getId()).isPresent());
    }

    /**
     * Integration test for deleting an ingredient that does not exist..
     */
    @Test
    void testDeleteIngredient_NotFound() throws Exception {
        mockMvc.perform(delete("/api/ingredients/9999"))
                .andExpect(status().isNoContent());
    }
}

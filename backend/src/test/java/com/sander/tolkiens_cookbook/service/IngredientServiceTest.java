package com.sander.tolkiens_cookbook.service;

import com.sander.tolkiens_cookbook.dto.IngredientDTO;
import com.sander.tolkiens_cookbook.exception.ResourceNotFoundException;
import com.sander.tolkiens_cookbook.model.Category;
import com.sander.tolkiens_cookbook.model.Ingredient;
import com.sander.tolkiens_cookbook.repository.IngredientDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link IngredientService}.
 */
class IngredientServiceTest {

    @InjectMocks
    private IngredientService ingredientService;

    @Mock
    private IngredientDAO ingredientDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test for retrieving all ingredients.
     */
    @Test
    void testGetAllIngredients() {
        Ingredient ingredient1 = new Ingredient("Tomato", Category.VEGETABLE);
        ingredient1.setId(1);
        Ingredient ingredient2 = new Ingredient("Chicken", Category.MEAT);
        ingredient2.setId(2);

        when(ingredientDAO.findAll()).thenReturn(List.of(ingredient1, ingredient2));

        List<IngredientDTO> result = ingredientService.getAllIngredients();

        assertEquals(2, result.size());
        assertEquals("Tomato", result.get(0).getName());
        assertEquals("Chicken", result.get(1).getName());
        verify(ingredientDAO, times(1)).findAll();
    }

    /**
     * Test for retrieving an ingredient by id.
     */
    @Test
    void testGetIngredientById_Found() {
        Ingredient ingredient = new Ingredient("Tomato", Category.VEGETABLE);
        ingredient.setId(1);

        when(ingredientDAO.findById(1)).thenReturn(Optional.of(ingredient));

        IngredientDTO result = ingredientService.getIngredientById(1);

        assertEquals("Tomato", result.getName());
        assertEquals("VEGETABLE", result.getCategory());
        verify(ingredientDAO, times(1)).findById(1);
    }

    /**
     * Test for retrieving an ingredient that does not exist by id.
     */
    @Test
    void testGetIngredientById_NotFound() {
        when(ingredientDAO.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> ingredientService.getIngredientById(1));
        verify(ingredientDAO, times(1)).findById(1);
    }

    /**
     * Test for creating a new ingredient.
     */
    @Test
    void testCreateIngredient_Success() {
        Ingredient ingredient = new Ingredient("Tomato", Category.VEGETABLE);

        when(ingredientDAO.existsByName("Tomato")).thenReturn(false);
        when(ingredientDAO.save(ingredient)).thenReturn(ingredient);

        Ingredient result = ingredientService.createIngredient(ingredient);

        assertEquals("Tomato", result.getName());
        verify(ingredientDAO, times(1)).existsByName("Tomato");
        verify(ingredientDAO, times(1)).save(ingredient);
    }

    /**
     * Test for attempting to create an ingredient that already exists.
     */
    @Test
    void testCreateIngredient_DuplicateName() {
        Ingredient ingredient = new Ingredient("Tomato", Category.VEGETABLE);

        when(ingredientDAO.existsByName("Tomato")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> ingredientService.createIngredient(ingredient));
        verify(ingredientDAO, times(1)).existsByName("Tomato");
        verify(ingredientDAO, never()).save(any());
    }

    /**
     * Test for retrieving an ingredient by name.
     */
    @Test
    void testGetIngredientByName() {
        Ingredient ingredient = new Ingredient("Tomato", Category.VEGETABLE);
        when(ingredientDAO.findByName("Tomato")).thenReturn(ingredient);

        Ingredient result = ingredientService.getIngredientByName("Tomato");

        assertNotNull(result);
        assertEquals("Tomato", result.getName());
        verify(ingredientDAO, times(1)).findByName("Tomato");
    }

    /**
     * Test for deleting an ingredient.
     */
    @Test
    void testDeleteIngredient() {
        doNothing().when(ingredientDAO).deleteById(1);

        ingredientService.deleteIngredient(1);

        verify(ingredientDAO, times(1)).deleteById(1);
    }

    /**
     * Test for updating an ingredient.
     */
    @Test
    void testUpdateIngredient_Success() {
        Ingredient updatedIngredient = new Ingredient("Updated Tomato", Category.VEGETABLE);
        Ingredient existingIngredient = new Ingredient("Old Tomato", Category.VEGETABLE);
        existingIngredient.setId(1);

        when(ingredientDAO.update(1, updatedIngredient)).thenReturn(updatedIngredient);

        Ingredient result = ingredientService.updateIngredient(1, updatedIngredient);

        assertEquals("Updated Tomato", result.getName());
        verify(ingredientDAO, times(1)).update(1, updatedIngredient);
    }
}

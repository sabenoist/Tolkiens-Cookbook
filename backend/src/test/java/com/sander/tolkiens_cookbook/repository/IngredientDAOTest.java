package com.sander.tolkiens_cookbook.repository;

import com.sander.tolkiens_cookbook.exception.ResourceNotFoundException;
import com.sander.tolkiens_cookbook.mapper.RecipeMapper;
import com.sander.tolkiens_cookbook.model.Category;
import com.sander.tolkiens_cookbook.model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link IngredientDAO}.
 */
class IngredientDAOTest {

    @InjectMocks
    private IngredientDAO ingredientDAO;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private RecipeIngredientRepository recipeIngredientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test that all ingredients can be found correctly.
     */
    @Test
    void testFindAll() {
        Ingredient ing1 = new Ingredient("Carrot", Category.VEGETABLE);
        Ingredient ing2 = new Ingredient("Chicken", Category.MEAT);

        when(ingredientRepository.findAll()).thenReturn(List.of(ing1, ing2));

        List<Ingredient> result = ingredientDAO.findAll();

        assertEquals(2, result.size());
        verify(ingredientRepository, times(1)).findAll();
    }

    /**
     * Test to find specific ingredients by id.
     */
    @Test
    void testFindById_Found() {
        Ingredient ingredient = new Ingredient("Salt", Category.OTHER);
        ingredient.setId(1);

        when(ingredientRepository.findById(1)).thenReturn(Optional.of(ingredient));

        Optional<Ingredient> result = ingredientDAO.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Salt", result.get().getName());
    }

    /**
     * Test attempt to find a specific ingredients by id that does not exist.
     */
    @Test
    void testFindById_NotFound() {
        when(ingredientRepository.findById(999)).thenReturn(Optional.empty());

        Optional<Ingredient> result = ingredientDAO.findById(999);

        assertFalse(result.isPresent());
    }

    /**
     * Test to find a specific ingredient by name.
     */
    @Test
    void testFindByName() {
        Ingredient ingredient = new Ingredient("Pepper", Category.OTHER);

        when(ingredientRepository.findByName("Pepper")).thenReturn(ingredient);

        Ingredient result = ingredientDAO.findByName("Pepper");

        assertEquals("Pepper", result.getName());
        verify(ingredientRepository, times(1)).findByName("Pepper");
    }

    /**
     * Test to check whether an ingredients exists.
     */
    @Test
    void testExistsByName() {
        when(ingredientRepository.existsByName("Sugar")).thenReturn(true);

        boolean exists = ingredientDAO.existsByName("Sugar");

        assertTrue(exists);
        verify(ingredientRepository, times(1)).existsByName("Sugar");
    }

    /**
     * Test to save a new ingredient in the database.
     */
    @Test
    void testSave() {
        Ingredient ingredient = new Ingredient("Honey", Category.OTHER);

        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);

        Ingredient saved = ingredientDAO.save(ingredient);

        assertEquals("Honey", saved.getName());
        verify(ingredientRepository, times(1)).save(ingredient);
    }

    /**
     * Test to delete a specific ingredient from the database.
     */
    @Test
    void testDeleteById() {
        doNothing().when(recipeIngredientRepository).deleteByIngredientId(1);
        doNothing().when(ingredientRepository).deleteById(1);

        ingredientDAO.deleteById(1);

        verify(recipeIngredientRepository, times(1)).deleteByIngredientId(1);
        verify(ingredientRepository, times(1)).deleteById(1);
    }

    /**
     * Test to update a specific ingredient from the database.
     */
    @Test
    void testUpdate_Found() {
        Ingredient existing = new Ingredient("Salt", Category.OTHER);
        existing.setId(1);

        Ingredient updated = new Ingredient("Sea Salt", Category.OTHER);

        when(ingredientRepository.findById(1)).thenReturn(Optional.of(existing));

        Ingredient result = ingredientDAO.update(1, updated);

        assertEquals("Sea Salt", result.getName());
        verify(ingredientRepository, times(1)).findById(1);
    }

    /**
     * Test to update a specific ingredient from the database that does not exist.
     */
    @Test
    void testUpdate_NotFound() {
        when(ingredientRepository.findById(999)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> ingredientDAO.update(999, new Ingredient("Cinnamon", Category.OTHER)),
                "Expected update() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Ingredient not found with ID: 999"));
    }
}

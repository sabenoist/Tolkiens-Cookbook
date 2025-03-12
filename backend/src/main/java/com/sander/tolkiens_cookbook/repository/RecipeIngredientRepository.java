package com.sander.tolkiens_cookbook.repository;

import com.sander.tolkiens_cookbook.model.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for managing {@link RecipeIngredient} entities.
 * <p>
 * Provides methods for deleting recipe-ingredient relationships based on recipe or ingredient IDs.
 * </p>
 */
@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Integer> {

    /**
     * Deletes all {@link RecipeIngredient} records associated with a specific recipe ID.
     * <p>
     * This is used when a recipe is deleted or its ingredients are being fully updated.
     * </p>
     * @param recipeId the ID of the recipe whose ingredient relations should be deleted.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM RecipeIngredient ri WHERE ri.recipe.id = :recipeId")
    void deleteByRecipeId(@Param("recipeId") int recipeId);

    /**
     * Deletes all {@link RecipeIngredient} records associated with a specific ingredient ID.
     * <p>
     * This is used when an ingredient is deleted from the system to clean up any existing references.
     * </p>
     * @param ingredientId the ID of the ingredient whose recipe relations should be deleted.
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM RecipeIngredient ri WHERE ri.ingredient.id = :ingredientId")
    void deleteByIngredientId(@Param("ingredientId") int ingredientId);
}
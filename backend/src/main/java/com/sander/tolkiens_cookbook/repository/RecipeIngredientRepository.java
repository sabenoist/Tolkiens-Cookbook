package com.sander.tolkiens_cookbook.repository;

import com.sander.tolkiens_cookbook.model.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM RecipeIngredient ri WHERE ri.recipe.id = :recipeId")
    void deleteByRecipeId(@Param("recipeId") int recipeId);
}

package com.sander.tolkiens_cookbook.repository;

import com.sander.tolkiens_cookbook.entity.Recipe;
import com.sander.tolkiens_cookbook.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Integer> {

}

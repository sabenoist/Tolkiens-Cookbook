package com.sander.tolkiens_cookbook.repository;

import com.sander.tolkiens_cookbook.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Recipe findByName(String name);
}
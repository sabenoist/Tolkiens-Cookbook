package com.sander.tolkiens_cookbook.dao;

import com.sander.tolkiens_cookbook.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    Ingredient findByName(String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM Ingredient")
    void clearIngredients();
}
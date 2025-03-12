package com.sander.tolkiens_cookbook.repository;

import com.sander.tolkiens_cookbook.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on {@link Ingredient} entities.
 *
 * Extends {@link JpaRepository} to provide built-in methods for entity management
 * such as save, find, delete, and custom query derivation by method names.
 */
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    /**
     * Finds an ingredient by its name.
     *
     * @param name the name of the ingredient to search for.
     * @return the {@link Ingredient} entity if found, otherwise {@code null}.
     */
    Ingredient findByName(String name);

    /**
     * Checks whether an ingredient with the given name exists.
     *
     * @param name the name to check for existence.
     * @return {@code true} if an ingredient with the given name exists, {@code false} otherwise.
     */
    boolean existsByName(String name);
}
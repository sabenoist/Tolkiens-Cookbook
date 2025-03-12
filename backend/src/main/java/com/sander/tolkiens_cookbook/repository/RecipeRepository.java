package com.sander.tolkiens_cookbook.repository;

import com.sander.tolkiens_cookbook.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Recipe} entities.
 *
 * Provides custom queries for searching recipes based on included/excluded ingredients,
 * keywords in instructions, and supports dynamic search flexibility.
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    /**
     * Finds a recipe by its unique name.
     *
     * @param name the name of the recipe.
     * @return the {@link Recipe} entity if found, or null otherwise.
     */
    Recipe findByName(String name);

    @Query(value = "SELECT DISTINCT r.* \n" +
            "FROM recipes r\n" +
            "JOIN recipe_ingredients ri ON r.recipe_id = ri.recipe_id\n" +
            "JOIN ingredients i ON i.ingredient_id = ri.ingredient_id\n" +
            "LEFT JOIN (\n" +
            "    SELECT r2.recipe_id\n" +
            "    FROM recipes r2\n" +
            "    JOIN recipe_ingredients ri2 ON r2.recipe_id = ri2.recipe_id\n" +
            "    JOIN ingredients i2 ON i2.ingredient_id = ri2.ingredient_id\n" +
            "    WHERE i2.ingredient_name IN (:excludeIngredients)\n" +
            ") excluded ON excluded.recipe_id = r.recipe_id\n" +
            "WHERE\n" +
            "(:includeEmpty OR i.ingredient_name IN (:includeIngredients))\n" +
            "AND (excluded.recipe_id IS NULL OR :excludeEmpty)\n" +
            "AND (:keywordPattern IS NULL OR LOWER(r.instructions) LIKE LOWER(:keywordPattern))\n" +
            "AND (:servings IS NULL OR r.servings = :servings)\n" +
            "GROUP BY r.recipe_id\n" +
            "HAVING COUNT(DISTINCT CASE WHEN i.ingredient_name IN (:includeIngredients) THEN i.ingredient_name END) = :includeCount\n",
            nativeQuery = true)
    List<Recipe> searchRecipes(
            @Param("includeIngredients") List<String> includeIngredients,
            @Param("excludeIngredients") List<String> excludeIngredients,
            @Param("includeCount") long includeCount,
            @Param("keywordPattern") String keywordPattern,
            @Param("includeEmpty") boolean includeEmpty,
            @Param("excludeEmpty") boolean excludeEmpty,
            @Param("servings") Integer servings
    );

}
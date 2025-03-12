package com.sander.tolkiens_cookbook.repository;

import com.sander.tolkiens_cookbook.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Recipe} entities.
 * <p>
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

    /**
     * Searches for recipes based on a flexible combination of included ingredients,
     * excluded ingredients, keyword in instructions, and counts of included ingredients.
     * <p>
     * Supports complex filtering scenarios where ingredients and keywords may or may not be provided.
     * </p>
     * @param includeIngredients List of ingredient names that must be included in the recipe. Can be empty.
     * @param excludeIngredients List of ingredient names that must be excluded from the recipe. Can be empty.
     * @param includeCount       The number of ingredients to match exactly when including specific ingredients.
     * @param keywordPattern     A keyword pattern (SQL LIKE pattern, wrapped in `%`) to search within the recipe instructions. Can be null.
     * @param includeEmpty       Boolean flag indicating whether to ignore inclusion filtering when no ingredients are provided.
     * @param excludeEmpty       Boolean flag indicating whether to ignore exclusion filtering when no ingredients are provided.
     * @return List of {@link Recipe} entities matching the search criteria.
     */
    @Query(value = "SELECT DISTINCT r.* FROM recipes r " +
            "JOIN recipe_ingredients ri ON r.recipe_id = ri.recipe_id " +
            "JOIN ingredients i ON i.ingredient_id = ri.ingredient_id " +
            "WHERE " +
            "(:includeEmpty OR i.ingredient_name IN (:includeIngredients)) " +
            "AND (:excludeEmpty OR r.recipe_id NOT IN ( " +
            "   SELECT r2.recipe_id FROM recipes r2 " +
            "   JOIN recipe_ingredients ri2 ON r2.recipe_id = ri2.recipe_id " +
            "   JOIN ingredients i2 ON i2.ingredient_id = ri2.ingredient_id " +
            "   WHERE i2.ingredient_name IN (:excludeIngredients) " +
            ")) " +
            "AND (:keywordPattern IS NULL OR LOWER(CAST(r.instructions AS TEXT)) LIKE LOWER(:keywordPattern)) " +
            "GROUP BY r.recipe_id " +
            "HAVING COUNT(DISTINCT CASE WHEN i.ingredient_name IN (:includeIngredients) THEN i.ingredient_name END) = :includeCount",
            nativeQuery = true)
    List<Recipe> searchRecipes(
            @Param("includeIngredients") List<String> includeIngredients,
            @Param("excludeIngredients") List<String> excludeIngredients,
            @Param("includeCount") long includeCount,
            @Param("keywordPattern") String keywordPattern,
            @Param("includeEmpty") boolean includeEmpty,
            @Param("excludeEmpty") boolean excludeEmpty
    );
}
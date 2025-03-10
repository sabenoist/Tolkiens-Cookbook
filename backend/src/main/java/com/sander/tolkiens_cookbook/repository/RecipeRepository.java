package com.sander.tolkiens_cookbook.repository;

import com.sander.tolkiens_cookbook.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Recipe findByName(String name);

    @Query("SELECT DISTINCT r FROM Recipe r " +
            "JOIN r.ingredients ri " +
            "JOIN ri.ingredient i " +
            "WHERE (:includeIngredients IS NULL OR i.name IN :includeIngredients) " +
            "AND r.id NOT IN ( " +
            "   SELECT r2.id FROM Recipe r2 " +
            "   JOIN r2.ingredients ri2 " +
            "   JOIN ri2.ingredient i2 " +
            "   WHERE i2.name IN :excludeIngredients " +
            ") " +
            "AND (:keywordPattern IS NULL OR LOWER(r.instructions) LIKE LOWER(:keywordPattern)) " +
            "GROUP BY r.id " +
            "HAVING COUNT(DISTINCT CASE WHEN i.name IN :includeIngredients THEN i.name END) = :includeCount")
    List<Recipe> searchRecipes(
            @Param("includeIngredients") List<String> includeIngredients,
            @Param("excludeIngredients") List<String> excludeIngredients,
            @Param("includeCount") long includeCount,
            @Param("keywordPattern") String keywordPattern);
}
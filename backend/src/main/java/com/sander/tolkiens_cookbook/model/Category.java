package com.sander.tolkiens_cookbook.model;

/**
 * Enumeration representing different categories of ingredients used in recipes.
 *
 * <p>This enum is used to classify ingredients into meaningful groups,
 * such as MEAT, FISH, VEGETABLE, etc., and is also used to determine properties
 * like whether an ingredient is vegetarian or not.</p>
 *
 * <p>The categories are used in {@link com.sander.tolkiens_cookbook.model.Ingredient}
 * for classification and business logic decisions, such as filtering recipes by ingredient types.</p>
 */
public enum Category {

    /** Represents meat-based ingredients (e.g., beef, chicken, pork). */
    MEAT,

    /** Represents fish or seafood-based ingredients (e.g., salmon, shrimp). */
    FISH,

    /** Represents vegetable ingredients (e.g., spinach, broccoli, carrots). */
    VEGETABLE,

    /** Represents fruit ingredients (e.g., apple, banana, orange). */
    FRUIT,

    /** Represents dairy products (e.g., milk, cheese, yogurt). */
    DAIRY,

    /** Represents grain-based ingredients (e.g., rice, wheat, oats). */
    GRAIN,

    /** Represents seeds and similar ingredients (e.g., sunflower seeds, chia seeds). */
    SEED,

    /** Represents other ingredients that do not fit into the predefined categories. */
    OTHER
}
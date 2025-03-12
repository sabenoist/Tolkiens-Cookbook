package com.sander.tolkiens_cookbook.model;

/**
 * Enumeration representing different categories of ingredients used in recipes.
 *
 * This enum is used to classify ingredients into meaningful groups,
 * such as MEAT, FISH, VEGETABLE, etc., and is also used to determine properties
 * like whether an ingredient is vegetarian or not.
 */
public enum Category {

    /** Represents meat-based ingredients */
    MEAT,

    /** Represents fish or seafood-based ingredients */
    FISH,

    /** Represents vegetable ingredients */
    VEGETABLE,

    /** Represents fruit ingredients */
    FRUIT,

    /** Represents dairy products */
    DAIRY,

    /** Represents grain-based ingredients */
    GRAIN,

    /** Represents seeds and similar ingredients */
    SEED,

    /** Represents other ingredients that do not fit into the predefined categories. */
    OTHER
}
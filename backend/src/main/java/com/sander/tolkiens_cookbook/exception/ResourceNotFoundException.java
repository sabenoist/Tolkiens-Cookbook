package com.sander.tolkiens_cookbook.exception;

/**
 * Custom exception used to indicate that a requested resource was not found.
 *
 * This exception is typically thrown when an entity (such as a Recipe or Ingredient)
 * with a specific ID does not exist in the database. It extends {@link RuntimeException}
 * so it can be handled globally and does not require explicit try-catch blocks.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code ResourceNotFoundException} with the specified detail message.
     *
     * @param message the detail message explaining which resource was not found
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
package com.sander.tolkiens_cookbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Global exception handler to manage and customize the application's error responses.
 *
 * <p>This class handles various common exceptions, including not found, type mismatches,
 * validation errors, and general unhandled exceptions. Responses are returned as
 * messages with appropriate HTTP status codes.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles requests for URLs that are not mapped to any controller.
     *
     * @param ex the exception thrown when no handler is found
     * @return a 404 NOT FOUND response with a custom message
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested URL was not found on this server.");
    }

    /**
     * Handles cases where a requested resource (e.g., entity) is not found.
     *
     * @param ex the ResourceNotFoundException containing the error message
     * @return a 404 NOT FOUND response with the exception message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles cases where a method argument does not match the expected type, including enums.
     *
     * @param ex the MethodArgumentTypeMismatchException
     * @return a 400 BAD REQUEST response with a detailed error message
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        if (ex.getRequiredType() != null && ex.getRequiredType().isEnum()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid value for enum " + ex.getRequiredType().getSimpleName() + ": " + ex.getValue());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + ex.getMessage());
    }

    /**
     * Handles validation errors when request parameters or bodies fail validation rules.
     *
     * @param ex the MethodArgumentNotValidException containing validation errors
     * @return a 400 BAD REQUEST response with the validation error message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationError(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: " + ex.getMessage());
    }

    /**
     * Handles any generic unhandled exception, providing a fallback error response.
     *
     * @param ex the general Exception
     * @return a 500 INTERNAL SERVER ERROR response with a generic error message
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        ex.printStackTrace(); // For debugging purposes, can be removed in production
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred.");
    }
}
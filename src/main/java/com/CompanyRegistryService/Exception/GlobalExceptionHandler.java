package com.CompanyRegistryService.Exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Validation Error Handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // ConstraintViolation (e.g. during save)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", ex.getMessage());
        error.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Custom Company Not Found
    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(CompanyNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", ex.getMessage());
        error.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // General Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAll(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Something went wrong. Please contact support.");
        error.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

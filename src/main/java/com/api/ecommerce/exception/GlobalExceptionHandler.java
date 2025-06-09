package com.api.ecommerce.exception;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.api.ecommerce.common.CommonConstant;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Custom Application Exceptions
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException ex) {
        return buildResponse(ex.getStatus(), ex.getMessage());
    }

    // 2. Validation Errors (DTOs with @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationError(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", errors);
    }

    // 3. Missing Required Parameters
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingParams(MissingServletRequestParameterException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Missing parameter: " + ex.getParameterName());
    }

    // 4. Path Variables or Type Conversion Errors
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Invalid value for: " + ex.getName());
    }

    // 5. Entity Not Found (custom or fallback)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNotFound(NoSuchElementException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Resource not found");
    }

    // 6. Access Denied (e.g., roles, permissions)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, "Access denied to the user");
    }

    // 7. Unsupported HTTP Methods
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        return buildResponse(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed: " + ex.getMethod());
    }

    // 8. Constraint Violation (e.g., JPA/Hibernate)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Constraint violation: " + ex.getMessage());
    }

    // 9. Database Exceptions
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrity(DataIntegrityViolationException ex) {
        return buildResponse(HttpStatus.CONFLICT, "Database error: " + ex.getRootCause().getMessage());
    }

    // 10. Catch-All Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, CommonConstant.CALL_FAILED_ON_SERVER + " Unexpected error: " + ex.getMessage());
    }

    // Utility method for building response
    private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
        return buildResponse(status, message, null);
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String message, Object details) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);
        if (details != null) {
            response.put("details", details);
        }
        return new ResponseEntity<>(response, status);
    }
}


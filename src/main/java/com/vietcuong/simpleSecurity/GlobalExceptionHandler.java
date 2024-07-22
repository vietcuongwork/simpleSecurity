package com.vietcuong.simpleSecurity;

import com.vietcuong.simpleSecurity.common.ApplicationError;
import com.vietcuong.simpleSecurity.response.GlobalResponse;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final GlobalResponse<?> globalResponse;

    public GlobalExceptionHandler(GlobalResponse<?> globalResponse) {
        this.globalResponse = globalResponse;
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public GlobalResponse<?> handleJwtException(JwtException ex) {
        // Log the exception for debugging purposes
        // logger.error("JWT Exception: {}", ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid Jwt token" + ex.getMessage().trim());
        return globalResponse.initializeResponse(
                ApplicationError.GlobalError.USER_REGISTRATION_ERROR, error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalResponse<?> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex) {
        Map<String, String> error = new HashMap<>();
        // Extracting the constraint name from the exception message
        String errorMessage = ex.getRootCause().getMessage();
        if (errorMessage.contains("username")) {
            error.put("username", "Username already exists");
        } else if (errorMessage.contains("email")) {
            error.put("email", "Email already exists");
        } else {
            error.put("unknown", "Duplicate entry error");
        }
        return globalResponse.initializeResponse(
                ApplicationError.GlobalError.USER_REGISTRATION_ERROR, error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalResponse<?> handleConstraintViolationException(
            ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach((violation) -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return globalResponse.initializeResponse(
                ApplicationError.GlobalError.USER_REGISTRATION_ERROR, errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalResponse<?> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        GlobalResponse<Map<String, String>> response = new GlobalResponse<>();
        response.setStatusCode(
                ApplicationError.GlobalError.USER_REGISTRATION_ERROR.getStatusCode());
        response.setDescription(
                ApplicationError.GlobalError.USER_REGISTRATION_ERROR.getDescription());
        Throwable rootCause = ex.getRootCause();
        Map<String, String> error = new HashMap<>();
        if (rootCause instanceof DateTimeParseException) {
            error.put("dateOfBirth",
                      "Invalid date format. Expected format is yyyy-MM-dd");
        }
        // For other exceptions, or if root cause is null, handle generically
        else {
            error.put("error", "Invalid request format");
        }
        return globalResponse.initializeResponse(
                ApplicationError.GlobalError.USER_REGISTRATION_ERROR, error);
    }
}

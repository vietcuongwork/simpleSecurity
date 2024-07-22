package com.vietcuong.simpleSecurity.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// Implementation of ConstraintValidator interface for ValidFullName annotation
public class FullNameValidator implements ConstraintValidator<ValidFullName, String> {

    @Override
    public void initialize(ValidFullName constraintAnnotation) {
        // Initialization logic if needed (currently empty)
    }

    // Initialize method, invoked during validator initialization
    @Override
    public boolean isValid(String fullName, ConstraintValidatorContext context) {
        // Check if fullName is null or empty
        if (fullName == null || fullName.trim().isEmpty()) {
            // Disable the default constraint violation message
            context.disableDefaultConstraintViolation();
            // Build a new constraint violation message for the empty fullName
            context.buildConstraintViolationWithTemplate("The full name cannot be empty or null")
                    .addConstraintViolation();
            return false; // Validation fails
        }

        // Check if fullName length is less than 3 or greater than 50 characters
        if (fullName.length() < 3 || fullName.length() > 50) {
            // Disable the default constraint violation message
            context.disableDefaultConstraintViolation();
            // Build a new constraint violation message for the invalid length
            context.buildConstraintViolationWithTemplate("The full name must be between 3 and 50 characters")
                    .addConstraintViolation();
            return false; // Validation fails
        }

        // Check if fullName contains only alphabetic characters and spaces
        if (!fullName.matches("^[a-zA-Z\\s]+$")) {
            // Disable the default constraint violation message
            context.disableDefaultConstraintViolation();
            // Build a new constraint violation message for the invalid format
            context.buildConstraintViolationWithTemplate("The full name must " + "consist only of alphabetic " + "characters " + "(a-z, A-Z) and " + "spaces, and cannot " + "include any numbers, " + "special" + " characters," + " or " + "punctuation")
                    .addConstraintViolation();
            return false; // Validation fails
        }

        // If all checks pass, return true indicating valid fullName
        return true;
    }

}
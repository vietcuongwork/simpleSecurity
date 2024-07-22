package com.vietcuong.simpleSecurity.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.trim().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Email cannot be empty or null.").addConstraintViolation();
            return false;
        }
        if (!email.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The email should start with alphanumeric characters, " +
                            "followed by '@', and end with a valid domain extension (e.g., '.com', '.org'). Example: " +
                            "'abc@example.com'.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

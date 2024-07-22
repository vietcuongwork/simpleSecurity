package com.vietcuong.simpleSecurity.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    @Override
    public void initialize(ValidUsername constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.trim().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The username cannot be empty or null")
                    .addConstraintViolation();
            return false;
        }
        if (username.length() < 3 || username.length() > 15) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The username must be between 3 and 15 characters")
                    .addConstraintViolation();
            return false;
        }
        if (!username.matches("^[a-zA-Z][a-zA-Z0-9]{2,14}$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The username must start with an alphabetic character (a-z, "
                            + "A-Z) and can only consist of alphanumeric characters (a-z, A-Z, 0-9)")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}

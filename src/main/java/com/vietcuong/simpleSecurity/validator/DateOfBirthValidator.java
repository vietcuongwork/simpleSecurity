package com.vietcuong.simpleSecurity.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class DateOfBirthValidator implements ConstraintValidator<ValidDateOfBirth, LocalDate> {
    @Override
    public void initialize(ValidDateOfBirth constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Date of birth cannot be empty or null")
                    .addConstraintViolation();
            return false;
        }

        LocalDate currentDate = LocalDate.now();
        int clientAge = Period.between(dateOfBirth, currentDate).getYears();
        if (clientAge < 18) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Client must be at least 18 years old")
                    .addConstraintViolation();
            return false;
        }

        return true;

    }
}

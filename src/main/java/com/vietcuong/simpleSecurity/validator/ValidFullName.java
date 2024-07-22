package com.vietcuong.simpleSecurity.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Define a custom annotation for validating full names
// This annotation is applied to fields
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
// Annotation is available at runtime for validation
@Constraint(validatedBy = FullNameValidator.class)
// Use FullNameValidator for validation logic
public @interface ValidFullName {
    String message() default "Invalid full name format.";
    // Default error message
    // for validation failure

    Class<?>[] groups() default {}; // Grouping constraints, if needed (not
    // used here)

    Class<? extends Payload>[] payload() default {}; // Additional data to be
    // carried with the annotation (not used here)
}

/*
Groups: Allows you to apply different sets of constraints in different
contexts, such as creating or updating an entity. This is particularly useful
for conditional validation scenarios.
Payload: Provides a way to attach additional metadata to constraint
violations, which can be used for advanced integration scenarios, such as
indicating the severity of a violation.*/

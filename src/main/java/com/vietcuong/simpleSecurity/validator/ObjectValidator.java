package com.vietcuong.simpleSecurity.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ObjectValidator<T> {
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    public Map<String, String> validate(T validateObject) {
        Set<ConstraintViolation<T>> violations = validator.validate(
                validateObject);
        if (!violations.isEmpty()) {
            return violations.stream().collect(Collectors.toMap(
                    violation -> violation.getPropertyPath().toString(),
                    ConstraintViolation::getMessage));
        }
        return Collections.emptyMap();
    }
}

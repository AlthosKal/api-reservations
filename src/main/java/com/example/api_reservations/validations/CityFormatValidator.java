package com.example.api_reservations.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CityFormatValidator implements ConstraintValidator<CityFormatConstraint, String> {
    @Override
    public void initialize(CityFormatConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext context) {
        return field != null &&
                field.length() ==3 &&
                field.matches("[A-Z]{3}") &&
                field.chars().allMatch(Character::isUpperCase);
    }
}

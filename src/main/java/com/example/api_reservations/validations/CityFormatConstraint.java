package com.example.api_reservations.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Annotation to enforce a specific format for city names. This constraint ensures that the annotated element (field or
 * method) contains a city name in the correct format (uppercase letters).
 */
@Documented
@Constraint(validatedBy = CityFormatValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CityFormatConstraint {

    /**
     * Defines the error message to be used when the constraint is violated.
     *
     * @return the error message string
     */
    String message() default "Invalid city format. It should be in uppercase letters.";

    /**
     * Specifies the processing groups with which this constraint is associated.
     *
     * @return an array of group classes
     */
    Class<?>[] groups() default {};

    /**
     * Specifies the payload with which this constraint is associated.
     *
     * @return an array of payload classes
     */
    Class<? extends Payload>[] payload() default {};
}

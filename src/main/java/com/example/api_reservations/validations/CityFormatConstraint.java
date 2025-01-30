package com.example.api_reservations.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Anotación para imponer un formato específico para los nombres de ciudades. Esta restricción asegura que el elemento
 * anotado (campo o método) contenga un nombre de ciudad en el formato correcto (letras mayúsculas).
 */
@Documented
@Constraint(validatedBy = CityFormatValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CityFormatConstraint {

    /**
     * Define el mensaje de error que se usará cuando se viole la restricción.
     *
     * @return el mensaje de error como cadena de texto
     */
    String message() default "Formato de ciudad inválido. Debe estar en letras mayúsculas.";

    /**
     * Especifica los grupos de procesamiento con los que está asociada esta restricción.
     *
     * @return un arreglo de clases de grupos
     */
    Class<?>[] groups() default {};

    /**
     * Especifica la carga útil con la que está asociada esta restricción.
     *
     * @return un arreglo de clases de carga útil
     */
    Class<? extends Payload>[] payload() default {};
}

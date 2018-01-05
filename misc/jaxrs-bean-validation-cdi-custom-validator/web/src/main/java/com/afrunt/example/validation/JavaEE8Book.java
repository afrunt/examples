package com.afrunt.example.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Andrii Frunt
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface JavaEE8Book {
    String message() default "{com.afrunt.example.validation.constraints.javaee8book.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

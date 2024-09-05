package com.swordfish.users.validation.annotation;

import com.swordfish.users.validation.imp.BirthDayValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BirthDayValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDayValid {
    String message() default "The field is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

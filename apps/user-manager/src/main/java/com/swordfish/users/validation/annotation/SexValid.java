package com.swordfish.users.validation.annotation;

import com.swordfish.users.validation.imp.SexValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SexValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SexValid {
    String message() default "The field is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

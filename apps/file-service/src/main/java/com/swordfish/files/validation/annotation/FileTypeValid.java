package com.swordfish.files.validation.annotation;

import com.swordfish.files.validation.imp.FileTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FileTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileTypeValid {
    String message() default "The field is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

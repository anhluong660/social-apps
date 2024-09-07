package com.swordfish.users.validation.imp;

import com.swordfish.users.validation.annotation.BirthDayValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Instant;

public class BirthDayValidator implements ConstraintValidator<BirthDayValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            Instant.parse(value);
        } catch (Exception ex) {
            return false;
        }

        return true;
    }
}

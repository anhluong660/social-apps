package com.swordfish.users.validation.imp;

import com.swordfish.users.validation.annotation.BirthDayValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class BirthDayValidator implements ConstraintValidator<BirthDayValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalDate date = LocalDate.parse(value);
        } catch (Exception ex) {
            return false;
        }

        return true;
    }
}

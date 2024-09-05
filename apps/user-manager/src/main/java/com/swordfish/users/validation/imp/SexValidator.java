package com.swordfish.users.validation.imp;

import com.swordfish.users.validation.annotation.SexValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

public class SexValidator implements ConstraintValidator<SexValid, String> {

    private static final Set<String> validList = new HashSet<>();

    static {
        validList.add("Male");
        validList.add("Female");
        validList.add("Other");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validList.contains(value);
    }
}

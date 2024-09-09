package com.swordfish.files.validation.imp;

import com.swordfish.files.validation.annotation.FileTypeValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class FileTypeValidator implements ConstraintValidator<FileTypeValid, String> {

    private static final Set<String> fileTypes = Set.of(
            "jpg", "jpeg", "png",
            "mp4"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return fileTypes.contains(value);
    }
}

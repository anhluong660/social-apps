package com.swordfish.files.validation;

import com.swordfish.utils.dto.InvalidResponse;
import com.swordfish.utils.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationError {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InvalidResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        }

        InvalidResponse response = new InvalidResponse();
        response.setError(ErrorCode.PARAMS_INVALID);
        response.setDetail(errors);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

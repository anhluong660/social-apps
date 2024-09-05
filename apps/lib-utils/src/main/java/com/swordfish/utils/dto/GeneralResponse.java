package com.swordfish.utils.dto;

import com.swordfish.utils.enums.ErrorMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralResponse<T> {
    private ErrorMessage message;
    private T data;

    public GeneralResponse(ErrorMessage message, T data) {
        this.message = message;
        this.data = data;
    }

    public static <T> GeneralResponse<T> success() {
        return new GeneralResponse<>(ErrorMessage.SUCCESS, null);
    }

    public static <T> GeneralResponse<T> fail() {
        return new GeneralResponse<>(ErrorMessage.FAIL, null);
    }

    public static <T> GeneralResponse<T> success(T data) {
        return new GeneralResponse<>(ErrorMessage.SUCCESS, data);
    }

    public static <T> GeneralResponse<T> fail(T data) {
        return new GeneralResponse<>(ErrorMessage.FAIL, data);
    }

    public static <T> GeneralResponse<T> of(ErrorMessage message) {
        return new GeneralResponse<>(message, null);
    }

    public static <T> GeneralResponse<T> of(ErrorMessage message, T data) {
        return new GeneralResponse<>(message, data);
    }
}

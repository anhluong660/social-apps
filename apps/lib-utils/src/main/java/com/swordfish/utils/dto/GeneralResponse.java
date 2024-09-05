package com.swordfish.utils.dto;

import com.swordfish.utils.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralResponse<T> {
    private ErrorCode error;
    private T data;

    public GeneralResponse(ErrorCode error, T data) {
        this.error = error;
        this.data = data;
    }

    public static <T> GeneralResponse<T> success() {
        return new GeneralResponse<>(ErrorCode.SUCCESS, null);
    }

    public static <T> GeneralResponse<T> fail() {
        return new GeneralResponse<>(ErrorCode.FAIL, null);
    }

    public static <T> GeneralResponse<T> success(T data) {
        return new GeneralResponse<>(ErrorCode.SUCCESS, data);
    }

    public static <T> GeneralResponse<T> fail(T data) {
        return new GeneralResponse<>(ErrorCode.FAIL, data);
    }

    public static <T> GeneralResponse<T> of(ErrorCode error) {
        return new GeneralResponse<>(error, null);
    }

    public static <T> GeneralResponse<T> of(ErrorCode error, T data) {
        return new GeneralResponse<>(error, data);
    }
}

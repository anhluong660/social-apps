package com.swordfish.utils.dto;

import com.swordfish.utils.enums.ErrorCode;
import com.swordfish.utils.enums.ErrorMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;

@Getter
@Setter
public class GeneralResponse<T> {
    private ErrorCode code;
    private ErrorMessage message;
    private T data;

    public GeneralResponse(ErrorCode code, ErrorMessage message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static GeneralResponse<?> success() {
        return new GeneralResponse<>(ErrorCode.OK, ErrorMessage.SUCCESS, Collections.emptyMap());
    }

    public static GeneralResponse<?> fail() {
        return new GeneralResponse<>(ErrorCode.OK, ErrorMessage.FAIL, Collections.emptyMap());
    }

    public static <T> GeneralResponse<T> success(T data) {
        return new GeneralResponse<>(ErrorCode.OK, ErrorMessage.SUCCESS, data);
    }

    public static <T> GeneralResponse<T> fail(T data) {
        return new GeneralResponse<>(ErrorCode.OK, ErrorMessage.FAIL, data);
    }

    public static GeneralResponse<?> of(ErrorMessage message) {
        return new GeneralResponse<>(ErrorCode.OK, message, Collections.emptyMap());
    }

    public static <T> GeneralResponse<T> of(ErrorMessage message, T data) {
        return new GeneralResponse<>(ErrorCode.OK, message, data);
    }
}

package com.swordfish.utils.dto;

import com.swordfish.utils.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Map;

@Getter
@Setter
public class GeneralMapResponse<T> {
    private ErrorCode error;
    private Map<String, T> data;

    public GeneralMapResponse(ErrorCode error, Map<String, T> data) {
        this.error = error;
        this.data = data;
    }

    public static <T> GeneralMapResponse<T> success() {
        return new GeneralMapResponse<>(ErrorCode.SUCCESS, Collections.emptyMap());
    }

    public static <T> GeneralMapResponse<T> fail() {
        return new GeneralMapResponse<>(ErrorCode.FAIL, Collections.emptyMap());
    }

    public static <T> GeneralMapResponse<T> success(Map<String, T> data) {
        return new GeneralMapResponse<>(ErrorCode.SUCCESS, data);
    }

    public static <T> GeneralMapResponse<T> fail(Map<String, T> data) {
        return new GeneralMapResponse<>(ErrorCode.FAIL, data);
    }

    public static <T> GeneralMapResponse<T> of(ErrorCode error) {
        return new GeneralMapResponse<>(error, Collections.emptyMap());
    }

    public static <T> GeneralMapResponse<T> of(ErrorCode error, Map<String, T> data) {
        return new GeneralMapResponse<>(error, data);
    }
}

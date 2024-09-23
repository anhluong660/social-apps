package com.swordfish.utils.dto;

import com.swordfish.utils.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class GeneralListResponse<T> {
    private ErrorCode error;
    private List<T> list;

    public GeneralListResponse(ErrorCode error, List<T> list) {
        this.error = error;
        this.list = list;
    }

    public static <T> GeneralListResponse<T> success() {
        return new GeneralListResponse<>(ErrorCode.SUCCESS, Collections.emptyList());
    }

    public static <T> GeneralListResponse<T> fail() {
        return new GeneralListResponse<>(ErrorCode.FAIL, Collections.emptyList());
    }

    public static <T> GeneralListResponse<T> success(List<T> list) {
        return new GeneralListResponse<>(ErrorCode.SUCCESS, list);
    }

    public static <T> GeneralListResponse<T> fail(List<T> list) {
        return new GeneralListResponse<>(ErrorCode.FAIL, list);
    }

    public static <T> GeneralListResponse<T> of(ErrorCode error) {
        return new GeneralListResponse<>(error, Collections.emptyList());
    }

    public static <T> GeneralListResponse<T> of(ErrorCode error, List<T> list) {
        return new GeneralListResponse<>(error, list);
    }
}

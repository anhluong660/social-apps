package com.swordfish.utils.dto;

import com.swordfish.utils.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class GeneralPageResponse<T> {
    private ErrorCode error;
    private Integer currentPage;
    private Integer pageSize;
    private Integer total;
    private List<T> list;

    public GeneralPageResponse() {
    }

    private GeneralPageResponse(ErrorCode error, Integer currentPage, Integer pageSize, Integer total, List<T> list) {
        this.error = error;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }

    public static <T> GeneralPageResponse<T> fail() {
        return new GeneralPageResponse<>(ErrorCode.FAIL, 0, 0, 0, Collections.emptyList());
    }

    public static <T> GeneralPageResponse<T> success() {
        return new GeneralPageResponse<>(ErrorCode.SUCCESS, 0, 0, 0, Collections.emptyList());
    }

    public static <T> GeneralPageResponse<T> of(ErrorCode error) {
        return new GeneralPageResponse<>(error, 0, 0, 0, Collections.emptyList());
    }
}

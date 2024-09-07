package com.swordfish.utils.dto;

import com.swordfish.utils.enums.ErrorCode;

import java.util.List;

public class GeneralPageResponse<T> {
    private ErrorCode error;
    private Integer currentPage;
    private Integer pageSize;
    private Integer total;
    private List<T> list;
}

package com.swordfish.utils.dto;

import java.util.List;

public class GeneralPageResponse<T> {
    private Integer currentPage;
    private Integer pageSize;
    private Integer total;
    private List<T> list;
}

package com.swordfish.utils.dto;

import com.swordfish.utils.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class InvalidResponse {
    private ErrorCode error;
    private Map<String, String> detail;
}

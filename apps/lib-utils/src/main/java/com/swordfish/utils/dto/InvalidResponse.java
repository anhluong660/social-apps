package com.swordfish.utils.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class InvalidResponse {

    private Integer num;

    private Map<String, String> errors;
}

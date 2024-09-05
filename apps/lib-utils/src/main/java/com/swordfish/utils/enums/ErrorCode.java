package com.swordfish.utils.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    OK(200),
    CREATED(201),
    NO_CONTENT(204),

    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    CONFLICT(409),

    INTERNAL_SERVER_ERROR(500);

    private final int value;

    ErrorCode(int value) {
        this.value = value;
    }
}

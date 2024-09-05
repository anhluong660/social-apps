package com.swordfish.users.dto.entities;

import com.swordfish.utils.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResult {

    private ErrorCode message;

    private String token;
}

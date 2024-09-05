package com.swordfish.users.dto.entities;

import com.swordfish.utils.enums.ErrorMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResult {

    private ErrorMessage message;

    private String token;
}

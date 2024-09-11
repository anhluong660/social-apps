package com.swordfish.users.dto.entities;

import com.swordfish.utils.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResult {

    private ErrorCode error;

    private String token;

    public static LoginResult error(ErrorCode error) {
        LoginResult loginResult = new LoginResult();
        loginResult.setError(error);
        loginResult.setToken("");
        return loginResult;
    }
}

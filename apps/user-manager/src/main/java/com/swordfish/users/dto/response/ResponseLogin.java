package com.swordfish.users.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseLogin {

    private String nickName;

    private String avatar;

    private String token;
}

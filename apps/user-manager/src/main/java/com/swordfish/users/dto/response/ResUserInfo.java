package com.swordfish.users.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResUserInfo {

    private Long userId;

    private String nickName;

    private String avatar;

    private String dateOfBirth;

    private String sex;
}

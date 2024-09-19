package com.swordfish.users.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResFriendInfo {

    private Long userId;

    private String nickName;

    private String avatarUrl;

    private Boolean online;
}

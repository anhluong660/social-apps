package com.swordfish.social.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseComment {

    private Long commentId;

    private Long authorId;

    private String authorName;

    private String authorAvatar;

    private String content;
}

package com.swordfish.social.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePost {

    private Long postId;

    private String authorName;

    private String authorAvatar;

    private String createTime;

    private String content;

    private String mediaLink;

    private Integer numLikes;

    private Integer numComments;
}

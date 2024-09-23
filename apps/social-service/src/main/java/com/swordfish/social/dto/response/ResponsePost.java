package com.swordfish.social.dto.response;

import com.swordfish.social.enums.PostType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePost {

    private Long postId;

    private Long authorId;

    private String authorName;

    private String authorAvatar;

    private String createTime;

    private PostType postType;

    private String content;

    private String mediaLink;

    private Boolean isLiked;

    private Integer numLikes;

    private Integer numComments;
}

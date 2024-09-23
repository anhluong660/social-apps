package com.swordfish.social.model;

import com.swordfish.social.enums.PostType;
import lombok.Data;

import java.util.Date;

@Data
public class PostModel {

    private Long id;

    private Long authorId;

    private Date createTime;

    private PostType postType;

    private String content;

    private String mediaLink;

    private Boolean isLiked;

    private Integer likeCount;

    private Integer commentCount;
}

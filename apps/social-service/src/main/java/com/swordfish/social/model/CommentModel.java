package com.swordfish.social.model;

import lombok.Data;

@Data
public class CommentModel {

    private Long id;

    private Long userId;

    private Long postId;

    private String content;

}

package com.swordfish.social.model;

import lombok.Data;

@Data
public class LikeModel {

    private Long id;

    private Long userId;

    private Long postId;
}

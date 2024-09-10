package com.swordfish.social.model;

import lombok.Data;

import java.util.Date;

@Data
public class PostModel {

    private Long id;

    private Long authorId;

    private Date createTime;

    private String content;

    private String mediaLink;
}

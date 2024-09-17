package com.swordfish.social.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {

    int isLiked(Long userId, Long postId);

    void likePost(Long userId, Long postId);

    void dislikePost(Long userId, Long postId);
}

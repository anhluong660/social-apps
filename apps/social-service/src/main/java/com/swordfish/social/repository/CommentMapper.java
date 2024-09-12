package com.swordfish.social.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    int countComments(Long postId);
}

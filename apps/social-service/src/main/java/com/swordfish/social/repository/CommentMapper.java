package com.swordfish.social.repository;

import com.swordfish.social.model.CommentModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    int countComments(Long postId);

    void insertComment(CommentModel commentModel);
}

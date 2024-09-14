package com.swordfish.social.repository;

import com.swordfish.social.model.CommentModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    int countComments(Long postId);

    List<CommentModel> findCommentByPostId(Long postId);

    void insertComment(CommentModel commentModel);
}

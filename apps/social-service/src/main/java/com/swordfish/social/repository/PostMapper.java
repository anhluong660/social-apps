package com.swordfish.social.repository;

import com.swordfish.social.model.PostModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    int insertNewPost(PostModel postModel);

    List<PostModel> findPostByUserId(Long userId);

    List<PostModel> findPostByUserIdList(List<Long> userIdList, Long userId);

    int existPost(Long postId);

    PostModel findPostById(Long postId);
}

package com.swordfish.social.repository;

import com.swordfish.social.model.PostModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    int insertNewPost(PostModel postModel);

    List<PostModel> findPostByAuthorId(Long authorId);

    List<PostModel> findPostByAuthorIdList(List<Long> authorIdList, Long userId);

    int existPost(Long postId);
}

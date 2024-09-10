package com.swordfish.social.repository;

import com.swordfish.social.model.PostModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {
    int insertNewPost(PostModel postModel);
}

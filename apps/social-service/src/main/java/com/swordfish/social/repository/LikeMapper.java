package com.swordfish.social.repository;

import com.swordfish.social.model.LikeModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LikeMapper {

    @Select("SELECT* FROM likes WHERE PostId = #{postId}")
    List<LikeModel> findByPostId(Long postId);

    List<LikeModel> findAll(Long userId, Long postId);
}

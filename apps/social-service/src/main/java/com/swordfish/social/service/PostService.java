package com.swordfish.social.service;

import com.swordfish.social.model.PostModel;
import com.swordfish.social.repository.PostMapper;
import com.swordfish.utils.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class PostService {

    @Autowired
    private PostMapper postMapper;

    public ErrorCode addNewPost(long authorId, String content, String mediaLink) {
        PostModel postModel = new PostModel();
        postModel.setAuthorId(authorId);
        postModel.setCreateTime(Date.from(Instant.now()));
        postModel.setContent(content);
        postModel.setMediaLink(mediaLink);

        postMapper.insertNewPost(postModel);
        return ErrorCode.SUCCESS;
    }
}

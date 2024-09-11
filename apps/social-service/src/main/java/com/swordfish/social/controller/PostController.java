package com.swordfish.social.controller;

import com.swordfish.social.dto.request.RequestNewPost;
import com.swordfish.social.dto.response.ResponsePost;
import com.swordfish.social.service.PostService;
import com.swordfish.utils.dto.GeneralPageResponse;
import com.swordfish.utils.dto.GeneralResponse;
import com.swordfish.utils.enums.ErrorCode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/new-post")
    public GeneralResponse<String> addNewPost(@RequestHeader("userId") Long userId,
            @Valid @RequestBody RequestNewPost request) {
        ErrorCode error = postService.addNewPost(userId, request.getContent(), request.getMediaLink());
        return GeneralResponse.of(error);
    }

    @GetMapping("/my-post-list")
    public GeneralPageResponse<ResponsePost> getMyPostList(@RequestHeader("userId") Long userId) {
        return postService.getMyPostList(userId);
    }
}

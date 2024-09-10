package com.swordfish.social.controller;

import com.swordfish.social.dto.request.RequestNewPost;
import com.swordfish.social.service.PostService;
import com.swordfish.utils.dto.GeneralResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
        postService.addNewPost(userId, request.getContent(), request.getMediaLink());
        return GeneralResponse.success();
    }
}

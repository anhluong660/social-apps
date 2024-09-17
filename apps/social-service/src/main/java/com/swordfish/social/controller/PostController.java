package com.swordfish.social.controller;

import com.swordfish.social.dto.request.RequestComment;
import com.swordfish.social.dto.request.RequestLikePost;
import com.swordfish.social.dto.request.RequestNewPost;
import com.swordfish.social.dto.response.ResponseComment;
import com.swordfish.social.dto.response.ResponseLikePost;
import com.swordfish.social.dto.response.ResponsePost;
import com.swordfish.social.service.PostService;
import com.swordfish.utils.dto.GeneralPageResponse;
import com.swordfish.utils.dto.GeneralResponse;
import com.swordfish.utils.enums.ErrorCode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/new-post")
    public GeneralResponse<String> addNewPost(@RequestHeader("userId") Long userId,
            @RequestBody RequestNewPost request) {
        if (request.invalid()) {
            return GeneralResponse.of(ErrorCode.PARAMS_INVALID);
        }

        ErrorCode error = postService.addNewPost(userId, request.getContent(), request.getMediaLink());
        return GeneralResponse.of(error);
    }

    @GetMapping("/my-post-list")
    public GeneralPageResponse<ResponsePost> getMyPostList(@RequestHeader("userId") Long userId) {
        return postService.getMyPostList(userId);
    }

    @GetMapping("/post-list")
    public GeneralPageResponse<ResponsePost> getPostList(@RequestHeader("userId") Long userId,
                                                     @RequestParam Integer page) {
        return postService.getPostList(userId, page);
    }

    @PostMapping("/like-post")
    public GeneralResponse<ResponseLikePost> likePost(@RequestHeader("userId") Long userId,
                                            @Valid @RequestBody RequestLikePost request) {
        if (!postService.existPost(request.getPostId())) {
            return GeneralResponse.of(ErrorCode.NOT_FOUND);
        }

        boolean isLiked = postService.likePost(userId, request.getPostId());

        ResponseLikePost response = new ResponseLikePost();
        response.setIsLiked(isLiked);
        return GeneralResponse.success(response);
    }

    @PostMapping("/comment")
    public GeneralResponse<String> postComment(@RequestHeader("userId") Long userId,
                                           @Valid @RequestBody RequestComment request) {
        if (!postService.existPost(request.getPostId())) {
            return GeneralResponse.of(ErrorCode.NOT_FOUND);
        }

        postService.insertComment(userId, request.getPostId(), request.getContent());
        return GeneralResponse.success();
    }

    @GetMapping("/comments/{postId}")
    public GeneralResponse<List<ResponseComment>> getCommentList(@PathVariable Long postId) {
        if (!postService.existPost(postId)) {
            return GeneralResponse.of(ErrorCode.NOT_FOUND);
        }

        List<ResponseComment> responseCommentList = postService.getCommentList(postId);
        return GeneralResponse.success(responseCommentList);
    }
}

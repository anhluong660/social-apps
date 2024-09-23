package com.swordfish.social.controller;

import com.swordfish.social.dto.request.RequestComment;
import com.swordfish.social.dto.request.RequestLikePost;
import com.swordfish.social.dto.request.RequestNewPost;
import com.swordfish.social.dto.response.ResponseComment;
import com.swordfish.social.dto.response.ResponseLikePost;
import com.swordfish.social.dto.response.ResponsePost;
import com.swordfish.social.service.PostService;
import com.swordfish.social.utils.ValidatorUtils;
import com.swordfish.utils.common.RequestContextUtil;
import com.swordfish.utils.dto.GeneralListResponse;
import com.swordfish.utils.dto.GeneralPageResponse;
import com.swordfish.utils.dto.GeneralResponse;
import com.swordfish.utils.enums.ErrorCode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private ValidatorUtils validatorUtils;

    @Autowired
    private PostService postService;

    @PostMapping("/new-post")
    public GeneralResponse<String> addNewPost(@RequestBody RequestNewPost request) {
        if (request.invalid()) {
            return GeneralResponse.of(ErrorCode.PARAMS_INVALID);
        }

        long userId = RequestContextUtil.getUserId();

        ErrorCode error = postService.addNewPost(userId, request.getContent(), request.getMediaLink());
        return GeneralResponse.of(error);
    }

    @GetMapping("/my-post-list/{userId}")
    public GeneralPageResponse<ResponsePost> getMyPostList(@PathVariable Long userId) {
        return postService.getMyPostList(userId);
    }

    @GetMapping("/post-list")
    public GeneralPageResponse<ResponsePost> getPostList(@RequestParam String type, @RequestParam Integer page) {
        if (validatorUtils.invalidPostType(type) || validatorUtils.invalidPage(page)) {
            return GeneralPageResponse.of(ErrorCode.PARAMS_INVALID);
        }

        long userId = RequestContextUtil.getUserId();
        return postService.getPostList(userId, type, page);
    }

    @GetMapping("/post/{postId}")
    public GeneralResponse<ResponsePost> getPost(@PathVariable Long postId) {
        if (!postService.existPost(postId)) {
            return GeneralResponse.of(ErrorCode.NOT_FOUND);
        }

        ResponsePost responsePost = postService.getPost(postId);
        return GeneralResponse.success(responsePost);
    }

    @PostMapping("/like-post")
    public GeneralResponse<ResponseLikePost> likePost(@Valid @RequestBody RequestLikePost request) {
        if (!postService.existPost(request.getPostId())) {
            return GeneralResponse.of(ErrorCode.NOT_FOUND);
        }

        long userId = RequestContextUtil.getUserId();
        boolean isLiked = postService.likePost(userId, request.getPostId());

        ResponseLikePost response = new ResponseLikePost();
        response.setIsLiked(isLiked);
        return GeneralResponse.success(response);
    }

    @PostMapping("/comment")
    public GeneralResponse<String> postComment(@Valid @RequestBody RequestComment request) {
        if (!postService.existPost(request.getPostId())) {
            return GeneralResponse.of(ErrorCode.NOT_FOUND);
        }

        long userId = RequestContextUtil.getUserId();
        postService.insertComment(userId, request.getPostId(), request.getContent());

        return GeneralResponse.success();
    }

    @GetMapping("/comments/{postId}")
    public GeneralListResponse<ResponseComment> getCommentList(@PathVariable Long postId) {
        if (!postService.existPost(postId)) {
            return GeneralListResponse.of(ErrorCode.NOT_FOUND);
        }

        List<ResponseComment> responseCommentList = postService.getCommentList(postId);
        return GeneralListResponse.success(responseCommentList);
    }
}

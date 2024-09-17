package com.swordfish.social.service;

import com.swordfish.social.dto.response.ResponseComment;
import com.swordfish.social.dto.response.ResponsePost;
import com.swordfish.social.integration.users.UserManagerFeign;
import com.swordfish.social.integration.users.dto.UserDto;
import com.swordfish.social.model.CommentModel;
import com.swordfish.social.model.PostModel;
import com.swordfish.social.repository.CommentMapper;
import com.swordfish.social.repository.LikeMapper;
import com.swordfish.social.repository.PostMapper;
import com.swordfish.social.utils.SocialUtils;
import com.swordfish.utils.common.SwordFishUtils;
import com.swordfish.utils.dto.GeneralPageResponse;
import com.swordfish.utils.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserManagerFeign userManagerFeign;

    @Autowired
    private SocialUtils socialUtils;

    public ErrorCode addNewPost(long authorId, String content, String mediaLink) {
        PostModel postModel = new PostModel();
        postModel.setAuthorId(authorId);
        postModel.setCreateTime(Date.from(Instant.now()));
        postModel.setContent(content);
        postModel.setMediaLink(mediaLink);

        try {
            int result = postMapper.insertNewPost(postModel);
            return result == 1 ? ErrorCode.SUCCESS : ErrorCode.FAIL;
        } catch (Exception ex) {
            log.error("Exception PostServer.addNewPost: {}", ex.getMessage());
            return ErrorCode.DATABASE_ERROR;
        }
    }

    public GeneralPageResponse<ResponsePost> getMyPostList(long authorId) {
        final UserDto authorInfo = userManagerFeign.getUserInfo(authorId);
        if (authorInfo == null) {
            return GeneralPageResponse.fail();
        }

        List<ResponsePost> postList = postMapper.findPostByAuthorId(authorId)
                .stream()
                .map(post -> {
                    String createTime = SwordFishUtils.convertToUTCStr(post.getCreateTime());
                    ResponsePost res = new ResponsePost();
                    res.setPostId(post.getId());
                    res.setAuthorId(authorInfo.getUserId());
                    res.setAuthorName(authorInfo.getNickName());
                    res.setAuthorAvatar(authorInfo.getAvatar());
                    res.setCreateTime(createTime);
                    res.setContent(post.getContent());
                    res.setMediaLink(post.getMediaLink());
                    res.setIsLiked(post.getIsLiked());
                    res.setNumLikes(post.getLikeCount());
                    res.setNumComments(post.getCommentCount());
                    return res;
                }).toList();

        GeneralPageResponse<ResponsePost> resultPage = new GeneralPageResponse<>();
        resultPage.setError(ErrorCode.SUCCESS);
        resultPage.setCurrentPage(1);
        resultPage.setPageSize(postList.size());
        resultPage.setTotal(postList.size());
        resultPage.setList(postList);
        return resultPage;
    }

    public GeneralPageResponse<ResponsePost> getPostList(long userId, int currentPage) {
        final long PAGE_SIZE = 10;

        final Set<Long> friendIds = new HashSet<>(userManagerFeign.getFriendIdList(userId));
        friendIds.add(userId);

        List<Long> allUserIdList = userManagerFeign.getAllUserIdList();

        List<PostModel> postModelList = postMapper.findPostByAuthorIdList(allUserIdList, userId);

        List<PostModel> postModelOrderList = postModelList.stream()
                .sorted((a, b) -> Integer.compare(
                        socialUtils.getPointOfPost(b, friendIds),
                        socialUtils.getPointOfPost(a, friendIds))
                )
                .skip((currentPage - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .toList();

        List<Long> authorIdList = postModelOrderList.stream()
                .map(PostModel::getAuthorId)
                .distinct()
                .toList();

        List<UserDto> userDtoList = userManagerFeign.getUserInfoList(authorIdList);

        List<ResponsePost> postList = postModelOrderList.stream()
                .map(post -> {
                    UserDto authorInfo = userDtoList.stream()
                            .filter(userDto -> Objects.equals(userDto.getUserId(), post.getAuthorId()))
                            .findFirst().orElse(null);

                    if (authorInfo == null) {
                        return null;
                    }

                    String createTime = SwordFishUtils.convertToUTCStr(post.getCreateTime());

                    ResponsePost res = new ResponsePost();
                    res.setPostId(post.getId());
                    res.setAuthorId(authorInfo.getUserId());
                    res.setAuthorName(authorInfo.getNickName());
                    res.setAuthorAvatar(authorInfo.getAvatar());
                    res.setCreateTime(createTime);
                    res.setContent(post.getContent());
                    res.setMediaLink(post.getMediaLink());
                    res.setIsLiked(post.getIsLiked());
                    res.setNumLikes(post.getLikeCount());
                    res.setNumComments(post.getCommentCount());
                    return res;
                })
                .filter(Objects::nonNull)
                .toList();

        GeneralPageResponse<ResponsePost> resultPage = new GeneralPageResponse<>();
        resultPage.setError(ErrorCode.SUCCESS);
        resultPage.setCurrentPage(currentPage);
        resultPage.setPageSize(postList.size());
        resultPage.setTotal(postModelList.size());
        resultPage.setList(postList);
        return resultPage;
    }

    public boolean existPost(long postId) {
        return postMapper.existPost(postId) > 0;
    }

    public boolean likePost(long userId, long postId) {
        if (likeMapper.isLiked(userId, postId) > 0) {
            likeMapper.dislikePost(userId, postId);
            return false;
        } else {
            likeMapper.likePost(userId, postId);
            return true;
        }
    }

    public void insertComment(long userId, long postId, String content) {
        CommentModel commentModel = new CommentModel();
        commentModel.setUserId(userId);
        commentModel.setPostId(postId);
        commentModel.setContent(content);

        commentMapper.insertComment(commentModel);
    }

    public List<ResponseComment> getCommentList(long postId) {
        List<CommentModel> commentModelList = commentMapper.findCommentByPostId(postId);
        if (commentModelList.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> authorIds = commentModelList.stream()
                .map(CommentModel::getUserId)
                .toList();

        List<UserDto> userDtoList = userManagerFeign.getUserInfoList(authorIds);

        List<ResponseComment> responseCommentList = new ArrayList<>();

        for (CommentModel commentModel : commentModelList) {
            long authorId = commentModel.getUserId();

            UserDto userInfo = userDtoList.stream()
                    .filter(userDto -> userDto.getUserId() == authorId)
                    .findFirst().orElse(null);

            if (userInfo == null) {
                continue;
            }

            ResponseComment responseComment = new ResponseComment();
            responseComment.setCommentId(commentModel.getId());
            responseComment.setAuthorId(userInfo.getUserId());
            responseComment.setAuthorName(userInfo.getNickName());
            responseComment.setAuthorAvatar(userInfo.getAvatar());
            responseComment.setContent(commentModel.getContent());

            responseCommentList.add(responseComment);
        }

        return responseCommentList;
    }
}

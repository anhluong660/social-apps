package com.swordfish.social.service;

import com.swordfish.social.dto.response.ResponsePost;
import com.swordfish.social.integration.users.UserManagerFeign;
import com.swordfish.social.integration.users.dto.UserDto;
import com.swordfish.social.model.PostModel;
import com.swordfish.social.repository.PostMapper;
import com.swordfish.utils.common.SwordFishUtils;
import com.swordfish.utils.dto.GeneralPageResponse;
import com.swordfish.utils.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserManagerFeign userManagerFeign;

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
                    res.setAuthorName(authorInfo.getNickName());
                    res.setAuthorAvatar(authorInfo.getAvatar());
                    res.setCreateTime(createTime);
                    res.setContent(post.getContent());
                    res.setMediaLink(post.getMediaLink());
                    res.setNumLikes(0);
                    res.setNumComments(0);
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
}

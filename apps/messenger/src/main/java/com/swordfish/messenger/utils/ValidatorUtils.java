package com.swordfish.messenger.utils;

import com.swordfish.messenger.enums.MessageType;
import com.swordfish.messenger.integration.users.UserManagerFeign;
import com.swordfish.utils.common.RequestContextUtil;
import com.swordfish.utils.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ValidatorUtils {

    @Autowired
    private UserManagerFeign userManagerFeign;

    public boolean invalidInputChatUser(long sendId, Long receiverId, MessageType messageType, String content) {
        if (receiverId == null || messageType == null || content == null) {
            return true;
        }

        return sendId == receiverId;
    }

    public boolean invalidInputGroupChat(String groupChatId, MessageType messageType, String content) {
        return groupChatId == null || messageType == null || content == null;
    }

    public ErrorCode checkMemberList(Set<Long> memberIdList) {
        if (memberIdList.size() < 2) {
            return ErrorCode.NOT_ENOUGH_ITEM;
        }

        long userId = RequestContextUtil.getUserId();
        if (memberIdList.contains(userId)) {
            return ErrorCode.FAIL;
        }

        List<Long> friendIdList = userManagerFeign.getFriendIdList(userId);

        if (memberIdList.stream().anyMatch(memberId -> !friendIdList.contains(memberId))) {
            return ErrorCode.NOT_FOUND;
        }

        return ErrorCode.SUCCESS;
    }

    public boolean invalidPage(Integer page) {
        return page == null || page <= 0;
    }
}

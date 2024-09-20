package com.swordfish.messenger.utils;

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

    public boolean invalidInputChatUser(long sendId, Long receiverId) {
        if (receiverId == null) {
            return true;
        }

        return sendId == receiverId;
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
}

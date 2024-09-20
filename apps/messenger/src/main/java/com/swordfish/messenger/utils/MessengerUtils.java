package com.swordfish.messenger.utils;

import com.swordfish.utils.common.RequestContextUtil;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MessengerUtils {

    public String createChatBoxId(long memberId1, long memberId2) {
        return memberId1 > memberId2 ? memberId1 + "-" + memberId2 : memberId2 + "-" + memberId1;
    }

    public boolean checkValidChatBoxId(String chatBoxId) {
        if (chatBoxId == null) {
            return false;
        }

        String[] arr = chatBoxId.split("-");
        if (arr.length != 2) {
            return false;
        }

        final long userId = RequestContextUtil.getUserId();

        return Arrays.stream(arr).anyMatch(uId -> Long.parseLong(uId) == userId);
    }
}

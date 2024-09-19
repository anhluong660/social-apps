package com.swordfish.messenger.utils;

import org.springframework.stereotype.Component;

@Component
public class MessengerUtils {

    public String createChatBoxId(long memberId1, long memberId2) {
        return memberId1 > memberId2 ? memberId1 + "_" + memberId2 : memberId2 + "_" + memberId1;
    }
}

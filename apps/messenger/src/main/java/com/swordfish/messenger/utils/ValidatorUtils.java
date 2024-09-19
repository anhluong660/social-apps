package com.swordfish.messenger.utils;

import org.springframework.stereotype.Component;

@Component
public class ValidatorUtils {

    public boolean invalidInputChatUser(long sendId, Long receiverId) {
        if (receiverId == null) {
            return true;
        }

        return sendId == receiverId;
    }
}

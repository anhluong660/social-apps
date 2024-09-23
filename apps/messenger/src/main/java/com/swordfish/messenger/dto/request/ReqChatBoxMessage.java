package com.swordfish.messenger.dto.request;

import com.swordfish.messenger.enums.MessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqChatBoxMessage {

    private Long receiverId;

    private MessageType messageType;

    private String content;
}

package com.swordfish.messenger.dto.request;

import com.swordfish.messenger.enums.MessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqGroupChatMessage {

    private String groupChatId;

    private MessageType messageType;

    private String content;
}

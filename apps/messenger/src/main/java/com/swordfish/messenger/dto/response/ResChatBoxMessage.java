package com.swordfish.messenger.dto.response;

import com.swordfish.messenger.enums.MessageType;
import com.swordfish.utils.dto.ResponseMessage;
import com.swordfish.utils.enums.SocketCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResChatBoxMessage extends ResponseMessage {

    private Long senderId;

    private MessageType messageType;

    private String content;

    public ResChatBoxMessage() {
        super(SocketCode.CHAT_USER);
    }
}

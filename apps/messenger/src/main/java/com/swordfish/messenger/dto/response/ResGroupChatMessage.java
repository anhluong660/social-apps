package com.swordfish.messenger.dto.response;

import com.swordfish.messenger.enums.MessageType;
import com.swordfish.utils.dto.ResponseMessage;
import com.swordfish.utils.enums.SocketCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResGroupChatMessage extends ResponseMessage {

    private String groupChatId;

    private Long senderId;

    private MessageType messageType;

    private String content;

    public ResGroupChatMessage() {
        super(SocketCode.CHAT_GROUP);
    }

}

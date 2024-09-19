package com.swordfish.messenger.dto.response;

import com.swordfish.messenger.enums.MessageType;
import com.swordfish.utils.dto.ResponseSocketBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMessage extends ResponseSocketBase {

    private String chatBoxId;

    private Long senderId;

    private MessageType messageType;

    private String content;
}

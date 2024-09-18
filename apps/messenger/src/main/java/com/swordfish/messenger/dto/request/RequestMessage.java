package com.swordfish.messenger.dto.request;

import com.swordfish.messenger.enums.MessageType;
import com.swordfish.utils.dto.RequestSocketBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestMessage extends RequestSocketBase {

    private String chatBoxId;

    private Long receiverId;

    private MessageType messageType;

    private String content;
}

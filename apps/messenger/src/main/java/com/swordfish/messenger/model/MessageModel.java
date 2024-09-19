package com.swordfish.messenger.model;

import com.swordfish.messenger.enums.MessageType;
import lombok.Data;

import java.util.Date;

@Data
public class MessageModel {

    private Long authorId;

    private MessageType type;

    private String content;

    private Date createTime;
}

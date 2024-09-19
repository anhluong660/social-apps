package com.swordfish.messenger.dto.entities;

import com.swordfish.messenger.enums.MessageType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageDto {

    private Long authorId;

    private MessageType type;

    private String content;

    private Date createTime;
}

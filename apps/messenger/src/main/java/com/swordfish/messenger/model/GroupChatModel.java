package com.swordfish.messenger.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("group_chat")
public class GroupChatModel {

    private String groupChatId;

    private String name;

    private List<Long> memberList;

    private List<MessageModel> messageList;
}

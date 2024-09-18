package com.swordfish.messenger.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("group_chat")
public class GroupChatModel {

    @Id
    private ObjectId id;

    private String name;

    private List<Long> memberList;

    private List<MessageModel> messageList;
}

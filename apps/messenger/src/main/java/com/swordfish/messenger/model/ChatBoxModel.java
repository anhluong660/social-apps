package com.swordfish.messenger.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("chat_box")
public class ChatBoxModel {

    @Id
    private ObjectId id;

    private List<Long> memberList;

    private List<MessageModel> messageList;
}

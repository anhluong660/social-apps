package com.swordfish.messenger.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("chat_box")
public class ChatBoxModel {

    private String chatBoxId;

    private List<MessageModel> messageList;
}

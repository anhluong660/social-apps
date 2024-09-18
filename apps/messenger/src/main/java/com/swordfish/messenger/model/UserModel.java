package com.swordfish.messenger.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("user")
public class UserModel {

    private Long userId;

    private List<String> chatBoxList;

    private List<String> groupChatList;
}

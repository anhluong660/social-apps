package com.swordfish.messenger.repository;

import com.swordfish.messenger.model.ChatBoxModel;
import com.swordfish.messenger.model.MessageModel;
import com.swordfish.messenger.repository.mongo.ChatBoxMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ChatBoxRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ChatBoxMongo chatBoxMongo;

    public boolean existsByChatBoxId(String chatBoxId) {
        return chatBoxMongo.existsByChatBoxId(chatBoxId);
    }

    public void findAndPushMessageByChatBoxId(String chatBoxId, MessageModel messageModel) {
        chatBoxMongo.findAndPushMessageByChatBoxId(chatBoxId, messageModel);
    }

    public ChatBoxModel save(ChatBoxModel chatBoxModel) {
        return chatBoxMongo.save(chatBoxModel);
    }
}

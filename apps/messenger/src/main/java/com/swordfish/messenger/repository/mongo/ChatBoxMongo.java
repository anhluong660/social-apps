package com.swordfish.messenger.repository.mongo;

import com.swordfish.messenger.model.ChatBoxModel;
import com.swordfish.messenger.model.MessageModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatBoxMongo extends MongoRepository<ChatBoxModel, ObjectId> {

    boolean existsByChatBoxId(String chatBoxId);

    @Update("{ $push: { 'messageList': ?1}}")
    void findAndPushMessageByChatBoxId(String chatBoxId, MessageModel messageModel);
}

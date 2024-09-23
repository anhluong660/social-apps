package com.swordfish.messenger.repository.mongo;

import com.swordfish.messenger.model.GroupChatModel;
import com.swordfish.messenger.model.MessageModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Update;

public interface GroupChatMongo extends MongoRepository<GroupChatModel, ObjectId> {

    @Update("{ $push: { 'messageList': ?1}}")
    void findAndPushMessageByGroupChatId(String groupChatId, MessageModel messageModel);
}

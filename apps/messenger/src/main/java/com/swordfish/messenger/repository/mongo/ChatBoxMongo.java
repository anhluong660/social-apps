package com.swordfish.messenger.repository.mongo;

import com.swordfish.messenger.model.ChatBoxModel;
import com.swordfish.messenger.model.MessageModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatBoxMongo extends MongoRepository<ChatBoxModel, ObjectId> {

    @Query("{ 'chatBoxId' : { $regex: ?0, $options: 'i' } }")
    List<ChatBoxModel> findChatBoxByMemberId(String memberId);

    boolean existsByChatBoxId(String chatBoxId);

    @Update("{ $push: { 'messageList': ?1}}")
    void findAndPushMessageByChatBoxId(String chatBoxId, MessageModel messageModel);
}

package com.swordfish.messenger.repository;

import com.swordfish.messenger.model.ChatBoxModel;
import com.swordfish.messenger.model.MessageModel;
import com.swordfish.messenger.repository.mongo.ChatBoxMongo;
import com.swordfish.utils.entities.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Slf4j
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

    public int countMessage(String chatBoxId) {
        Aggregation aggregation = newAggregation(
            match(Criteria.where("chatBoxId").is(chatBoxId)),
                project().and("messageList").size().as("messageCount")
        );

        record MessageCounter(int messageCount){}

        AggregationResults<MessageCounter> results = mongoTemplate.aggregate(aggregation, "chat_box", MessageCounter.class);

        MessageCounter messageCounter = results.getUniqueMappedResult();
        return messageCounter != null ? messageCounter.messageCount() : 0;
    }

    public List<MessageModel> getMessageList(String chatBoxId, int offset, int size) {
        Query query = new Query();
        query.addCriteria(Criteria.where("chatBoxId").is(chatBoxId));
        query.fields().slice("messageList", offset, size);

        try {
            ChatBoxModel chatBoxModel = mongoTemplate.findOne(query, ChatBoxModel.class);
            if (chatBoxModel != null) {
                return chatBoxModel.getMessageList();
            } else {
                log.warn("ChatBoxRepository.getMessageList chatBoxModel = null, chatBoxId={}", chatBoxId);
            }
        } catch (Exception ignore) { }

        return Collections.emptyList();
    }
}

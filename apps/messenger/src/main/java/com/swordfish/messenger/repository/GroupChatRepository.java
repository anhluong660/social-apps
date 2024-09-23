package com.swordfish.messenger.repository;

import com.swordfish.messenger.model.ChatBoxModel;
import com.swordfish.messenger.model.GroupChatModel;
import com.swordfish.messenger.model.MessageModel;
import com.swordfish.messenger.repository.mongo.GroupChatMongo;
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

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@Slf4j
@Repository
public class GroupChatRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GroupChatMongo groupChatMongo;

    public GroupChatModel save(GroupChatModel groupChatModel) {
        return groupChatMongo.save(groupChatModel);
    }

    public boolean existsByGroupChatId(String groupChatId) {
        return groupChatMongo.existsByGroupChatId(groupChatId);
    }

    public List<Long> getMemberIdsByGroupChatId(String groupChatId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("groupChatId").is(groupChatId));
        query.fields().exclude("_id", "groupChatId", "name", "messageList");
        GroupChatModel groupChatModel = mongoTemplate.findOne(query, GroupChatModel.class);

        if (groupChatModel != null) {
            return groupChatModel.getMemberList();
        }

        return Collections.emptyList();
    }

    public void findAndPushMessageByChatBoxId(String groupChatId, MessageModel messageModel) {
        groupChatMongo.findAndPushMessageByGroupChatId(groupChatId, messageModel);
    }

    public List<GroupChatModel> findGroupListByMemberId(long memberId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberList").is(memberId));
        query.fields().exclude("_id", "messageList");
        return mongoTemplate.find(query, GroupChatModel.class);
    }

    public int countMessage(String groupChatId) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("groupChatId").is(groupChatId)),
                project().and("messageList").size().as("messageCount")
        );

        record MessageCounter(int messageCount){}

        AggregationResults<MessageCounter> results = mongoTemplate.aggregate(aggregation, GroupChatModel.class, MessageCounter.class);

        MessageCounter messageCounter = results.getUniqueMappedResult();
        return messageCounter != null ? messageCounter.messageCount() : 0;
    }

    public List<MessageModel> getMessageList(String groupChatId, int offset, int size) {
        Query query = new Query();
        query.addCriteria(Criteria.where("groupChatId").is(groupChatId));
        query.fields().slice("messageList", offset, size);

        try {
            GroupChatModel groupChatModel = mongoTemplate.findOne(query, GroupChatModel.class);
            if (groupChatModel != null) {
                return groupChatModel.getMessageList();
            } else {
                log.warn("GroupChatRepository.getMessageList groupChatModel = null, groupChatId={}", groupChatId);
            }
        } catch (Exception ignore) { }

        return Collections.emptyList();
    }
}

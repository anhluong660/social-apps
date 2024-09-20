package com.swordfish.messenger.repository;

import com.swordfish.messenger.model.GroupChatModel;
import com.swordfish.messenger.repository.mongo.GroupChatMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupChatRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GroupChatMongo groupChatMongo;

    public GroupChatModel save(GroupChatModel groupChatModel) {
        return groupChatMongo.save(groupChatModel);
    }

    public List<GroupChatModel> findGroupListByMemberId(long memberId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberList").is(memberId));
        query.fields().exclude("_id", "messageList");
        return mongoTemplate.find(query, GroupChatModel.class);
    }
}

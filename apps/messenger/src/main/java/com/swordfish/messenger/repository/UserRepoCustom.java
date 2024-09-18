package com.swordfish.messenger.repository;

import com.swordfish.messenger.model.ChatBoxModel;
import com.swordfish.messenger.model.UserModel;
import com.swordfish.utils.entities.Pair;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class UserRepoCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<String> getChatBoxListByUserId(long userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        query.fields().include("chatBoxList").exclude("_id");

        UserModel userModel = mongoTemplate.findOne(query, UserModel.class);
        if (userModel == null) {
            return Collections.emptyList();
        }

        return userModel.getChatBoxList();
    }

    public Map<String, List<Long>> getUserIdListByChatBoxList(List<String> chatBoxList) {
        List<ObjectId> objectIdList = chatBoxList.stream()
                .map(ObjectId::new)
                .toList();

        Query query = new Query();
        Criteria.where("_id").in(objectIdList);
        query.fields().include("memberList");

        return mongoTemplate.find(query, ChatBoxModel.class).stream()
                .collect(Collectors.toMap(
                        model -> model.getId().toString(),
                        ChatBoxModel::getMemberList,
                        (oldValue, newValue) -> newValue,
                        HashMap::new
                ));
    }
}

package com.swordfish.users.repository;

import com.swordfish.users.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepoCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Long> findAllUserIds() {
        Query query = new Query();
        query.fields().include("userId").exclude("_id");

        return mongoTemplate.find(query, UserModel.class).stream()
                .map(UserModel::getUserId)
                .toList();
    }
}

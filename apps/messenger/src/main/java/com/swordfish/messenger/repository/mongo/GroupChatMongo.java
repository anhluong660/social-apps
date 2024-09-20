package com.swordfish.messenger.repository.mongo;

import com.swordfish.messenger.model.GroupChatModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupChatMongo extends MongoRepository<GroupChatModel, ObjectId> {
}

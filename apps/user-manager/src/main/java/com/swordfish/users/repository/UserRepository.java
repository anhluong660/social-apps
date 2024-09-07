package com.swordfish.users.repository;

import com.swordfish.users.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserModel, String> {
    UserModel findByUserId(Long userId);
}

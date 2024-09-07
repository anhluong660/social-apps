package com.swordfish.users.repository;

import com.swordfish.users.model.AccountModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<AccountModel, String> {
    boolean existsByUsername(String username);
    AccountModel findByUsername(String username);
}

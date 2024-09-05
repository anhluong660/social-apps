package com.swordfish.users.repository;

import com.swordfish.users.model.AccountModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<AccountModel, Integer> {
    boolean existsByUsername(String username);
    Optional<AccountModel> findByUsername(String username);
}

package com.swordfish.users.repository;

import com.swordfish.users.model.AccountDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<AccountDO, Integer> {
    boolean existsByUsername(String username);
    Optional<AccountDO> findByUsername(String username);
}

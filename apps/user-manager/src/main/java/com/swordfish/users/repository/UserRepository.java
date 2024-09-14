package com.swordfish.users.repository;

import com.swordfish.users.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

public interface UserRepository extends MongoRepository<UserModel, String> {

    UserModel findByUserId(Long userId);

    @Query("{ 'userId': { $in: ?0 } }")
    List<UserModel> findAllByUserIds(List<Long> userIds);

    boolean existsByUserId(Long userId);

    @Update("{ $push: { 'requests': ?1 } }")
    void findAndAddRequesterByUserId(Long userId, Long requesterId);

    @Update("{ $push: { 'inviters': ?1 } }")
    void findAndAddInviterByUserId(Long userId, Long inviterId);

    @Update("{ $pull: { 'requests': ?1, 'inviters': ?1 }, $push: { 'friends': ?1 } }")
    void findAndMatchFriendByUserId(Long userId, Long friendId);

    @Update("{ $pull: { 'friends': ?1 } }")
    void findAndRemoveFriendByUserId(Long userId, Long friendId);
}

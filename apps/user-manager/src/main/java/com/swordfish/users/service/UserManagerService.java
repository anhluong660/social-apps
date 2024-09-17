package com.swordfish.users.service;

import com.swordfish.users.dto.entities.UserDto;
import com.swordfish.users.dto.response.ResFriendInfo;
import com.swordfish.users.dto.response.ResUserInfo;
import com.swordfish.users.model.UserModel;
import com.swordfish.users.repository.UserRepoCustom;
import com.swordfish.users.repository.UserRepository;
import com.swordfish.utils.enums.ErrorCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class UserManagerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepoCustom userRepoCustom;

    public ResUserInfo getUserInfo(long userId) {
        UserModel userModel = userRepository.findByUserId(userId);
        if (userModel == null) {
            return null;
        }

        String dateOfBirth = userModel.getDateOfBirth()
                .toInstant().toString();

        ResUserInfo resUserInfo = new ResUserInfo();
        resUserInfo.setNickName(userModel.getNickName());
        resUserInfo.setAvatar(userModel.getAvatarUrl());
        resUserInfo.setDateOfBirth(dateOfBirth);
        resUserInfo.setSex(userModel.getSex());
        return resUserInfo;
    }

    public UserDto getUserDtoInfo(long userId) {
        UserModel userModel = userRepository.findByUserId(userId);
        if (userModel == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setUserId(userModel.getUserId());
        userDto.setNickName(userModel.getNickName());
        userDto.setAvatar(userModel.getAvatarUrl());
        return userDto;
    }

    public List<UserDto> getUserDtoInfoList(List<Long> userIds) {
        return userRepository.findAllByUserIds(userIds).stream()
                .map(userModel -> {
                    UserDto userDto = new UserDto();
                    userDto.setUserId(userModel.getUserId());
                    userDto.setNickName(userModel.getNickName());
                    userDto.setAvatar(userModel.getAvatarUrl());
                    return userDto;
                }).toList();
    }

    public boolean existUserById(long userId) {
        return userRepository.existsByUserId(userId);
    }

    @Transactional
    public ErrorCode addFriend(long userId, long friendId) {
        UserModel userModel = userRepository.findByUserId(userId);
        if (userModel == null) {
            return ErrorCode.USER_INFO_NULL;
        }

        if (userModel.getFriends().contains(friendId)) {
            return ErrorCode.ACCOUNT_EXIST;
        }

        if (userModel.getRequests().contains(friendId)) {
            return ErrorCode.FAIL;
        }

        if (userModel.getInviters().contains(friendId)) {
            userRepository.findAndMatchFriendByUserId(userId, friendId);
            userRepository.findAndMatchFriendByUserId(friendId, userId);
        } else {
            userRepository.findAndAddRequesterByUserId(userId, friendId);
            userRepository.findAndAddInviterByUserId(friendId, userId);
        }

        return ErrorCode.SUCCESS;
    }

    @Transactional
    public ErrorCode removeFriend(long userId, long friendId) {
        userRepository.findAndRemoveFriendByUserId(userId, friendId);
        userRepository.findAndRemoveFriendByUserId(friendId, userId);
        return ErrorCode.SUCCESS;
    }

    private List<ResFriendInfo> getFriendInfoListByUserIds(List<Long> userIds) {
        List<UserModel> friendListModel = userRepository.findAllByUserIds(userIds);

        return friendListModel.stream()
                .map(friendModel -> {
                    ResFriendInfo friendInfo = new ResFriendInfo();
                    BeanUtils.copyProperties(friendModel, friendInfo);
                    return friendInfo;
                }).toList();
    }

    public List<ResFriendInfo> getAllFriend(long userId) {
        UserModel userModel = userRepository.findByUserId(userId);
        if (userModel == null) {
            return Collections.emptyList();
        }

        return getFriendInfoListByUserIds(userModel.getFriends());
    }

    public List<ResFriendInfo> getAllInviter(long userId) {
        UserModel userModel = userRepository.findByUserId(userId);
        if (userModel == null) {
            return Collections.emptyList();
        }

        return getFriendInfoListByUserIds(userModel.getInviters());
    }

    public List<Long> getFriendIdList(long userId) {
        UserModel userModel = userRepository.findByUserId(userId);
        if (userModel == null) {
            return Collections.emptyList();
        }

        return userModel.getFriends();
    }

    public List<Long> getAllUserIdList() {
        return userRepoCustom.findAllUserIds();
    }
}

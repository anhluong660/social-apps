package com.swordfish.users.service;

import com.swordfish.users.dto.response.ResUserInfo;
import com.swordfish.users.model.UserModel;
import com.swordfish.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManagerService {

    @Autowired
    private UserRepository userRepository;

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
}

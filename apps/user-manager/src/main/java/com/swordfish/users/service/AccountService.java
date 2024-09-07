package com.swordfish.users.service;

import com.swordfish.users.dto.entities.LoginResult;
import com.swordfish.users.dto.request.RequestLogin;
import com.swordfish.users.dto.request.RequestRegister;
import com.swordfish.users.model.AccountModel;
import com.swordfish.users.model.UserModel;
import com.swordfish.users.repository.AccountRepository;
import com.swordfish.users.repository.UserRepository;
import com.swordfish.users.utils.JwtUtil;
import com.swordfish.users.utils.UserIdGenerator;
import com.swordfish.utils.common.SwordFishUtils;
import com.swordfish.utils.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserIdGenerator userIdGenerator;

    @Autowired
    private JwtUtil jwtUtil;

    public ErrorCode register(RequestRegister request) {
        if (accountRepository.existsByUsername(request.getUsername())) {
            return ErrorCode.ACCOUNT_EXIST;
        }

        Long newUserId = userIdGenerator.getNewId();
        String passwordHash = SwordFishUtils.hashMd5(request.getPassword());
        Date dateOfBirth = SwordFishUtils.convertToUTCDate(request.getDateOfBirth());

        AccountModel accountModel = new AccountModel();
        accountModel.setUserId(newUserId);
        accountModel.setUsername(request.getUsername());
        accountModel.setPassword(passwordHash);
        accountRepository.save(accountModel);

        UserModel userModel = new UserModel();
        userModel.setUserId(newUserId);
        userModel.setNickName(request.getFullName());
        userModel.setAvatarUrl("");
        userModel.setDateOfBirth(dateOfBirth);
        userModel.setSex(request.getSex());
        userModel.setFriends(new ArrayList<>());
        userModel.setInviters(new ArrayList<>());
        userModel.setRequests(new ArrayList<>());
        userRepository.save(userModel);

        return ErrorCode.SUCCESS;
    }

    public LoginResult login(RequestLogin request) {
        LoginResult loginResult = new LoginResult();

        AccountModel accountModel = accountRepository.findByUsername(request.getUsername());
        if (accountModel == null) {
            loginResult.setError(ErrorCode.ACCOUNT_NOT_EXIST);
            return loginResult;
        }

        String passwordHash = SwordFishUtils.hashMd5(request.getPassword());

        if (!accountModel.getPassword().equals(passwordHash)) {
            loginResult.setError(ErrorCode.PASSWORD_INCORRECT);
            return loginResult;
        }

        // get user info
        UserModel userModel = userRepository.findByUserId(accountModel.getUserId());
        if (userModel == null) {
            loginResult.setError(ErrorCode.USER_INFO_NULL);
            return loginResult;
        }

        String jwtToken = jwtUtil.generateToken(accountModel.getUserId());

        loginResult.setError(ErrorCode.SUCCESS);
        loginResult.setNickName(userModel.getNickName());
        loginResult.setAvatar(userModel.getAvatarUrl());
        loginResult.setToken(jwtToken);

        return loginResult;
    }
}

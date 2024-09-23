package com.swordfish.users.service;

import com.swordfish.users.dto.entities.AccountDto;
import com.swordfish.users.dto.entities.LoginResult;
import com.swordfish.users.dto.request.RequestLogin;
import com.swordfish.users.dto.request.RequestRegister;
import com.swordfish.users.model.AccountModel;
import com.swordfish.users.model.UserModel;
import com.swordfish.users.repository.AccountRepository;
import com.swordfish.users.repository.UserRepository;
import com.swordfish.users.utils.JwtUtil;
import com.swordfish.users.utils.UserIdGenerator;
import com.swordfish.utils.common.DateUtil;
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
        String username = request.getUsername().toLowerCase();

        if (accountRepository.existsByUsername(username)) {
            return ErrorCode.ACCOUNT_EXIST;
        }

        Long newUserId = userIdGenerator.getNewId();
        String passwordHash = SwordFishUtils.hashMd5(request.getPassword());
        Date dateOfBirth = DateUtil.convertToUTCDate(request.getDateOfBirth());

        AccountModel accountModel = new AccountModel();
        accountModel.setUserId(newUserId);
        accountModel.setUsername(username);
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
        String username = request.getUsername().toLowerCase();

        AccountModel accountModel = accountRepository.findByUsername(username);
        if (accountModel == null) {
            return LoginResult.error(ErrorCode.ACCOUNT_NOT_EXIST);
        }

        String passwordHash = SwordFishUtils.hashMd5(request.getPassword());

        if (!accountModel.getPassword().equals(passwordHash)) {
            return LoginResult.error(ErrorCode.PASSWORD_INCORRECT);
        }

        String jwtToken = jwtUtil.generateToken(accountModel.getUserId());

        LoginResult loginResult = new LoginResult();
        loginResult.setError(ErrorCode.SUCCESS);
        loginResult.setToken(jwtToken);

        return loginResult;
    }

    public AccountDto getAccountInfo(String token) throws Exception {
        token = token.substring(7);
        String userId = jwtUtil.getUserId(token);

        if (jwtUtil.isTokenExpired(token)) {
            throw new RuntimeException();
        }

        AccountDto accountDto = new AccountDto();
        accountDto.setError(ErrorCode.SUCCESS);
        accountDto.setUserId(Long.parseLong(userId));
        return accountDto;
    }
}

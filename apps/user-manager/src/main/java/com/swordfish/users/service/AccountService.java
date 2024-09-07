package com.swordfish.users.service;

import com.swordfish.users.dto.entities.LoginResult;
import com.swordfish.users.dto.request.RequestLogin;
import com.swordfish.users.dto.request.RequestRegister;
import com.swordfish.users.model.AccountModel;
import com.swordfish.users.repository.AccountRepository;
import com.swordfish.users.utils.JwtUtil;
import com.swordfish.users.utils.UserIdGenerator;
import com.swordfish.utils.common.SwordFishUtils;
import com.swordfish.utils.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

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
        LocalDate dateOfBirth = LocalDate.parse(request.getDateOfBirth());

        AccountModel accountModel = new AccountModel();
        accountModel.setUserId(newUserId);
        accountModel.setUsername(request.getUsername());
        accountModel.setPassword(passwordHash);
        accountModel.setFullName(request.getFullName());
        accountModel.setDateOfBirth(dateOfBirth);
        accountModel.setSex(request.getSex());

        accountRepository.save(accountModel);

        return ErrorCode.SUCCESS;
    }

    public LoginResult login(RequestLogin request) {
        LoginResult loginResult = new LoginResult();

        AccountModel accountModel = accountRepository.findByUsername(request.getUsername());
        if (accountModel == null) {
            loginResult.setMessage(ErrorCode.ACCOUNT_NOT_EXIST);
            return loginResult;
        }

        String passwordHash = SwordFishUtils.hashMd5(request.getPassword());

        if (!accountModel.getPassword().equals(passwordHash)) {
            loginResult.setMessage(ErrorCode.PASSWORD_INCORRECT);
            return loginResult;
        }

        String jwtToken = jwtUtil.generateToken(accountModel.getUserId());

        loginResult.setMessage(ErrorCode.SUCCESS);
        loginResult.setToken(jwtToken);

        return loginResult;
    }
}

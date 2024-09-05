package com.swordfish.users.service;

import com.swordfish.users.dto.request.RequestRegister;
import com.swordfish.users.model.AccountModel;
import com.swordfish.users.repository.AccountRepository;
import com.swordfish.utils.enums.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public ErrorMessage register(RequestRegister request) {
        if (accountRepository.existsByUsername(request.getUsername())) {
            return ErrorMessage.ACCOUNT_EXIST;
        }

        byte[] passwordBytes = request.getPassword().getBytes(StandardCharsets.UTF_8);
        String passwordHash = DigestUtils.md5DigestAsHex(passwordBytes);

        LocalDate dateOfBirth = LocalDate.parse(request.getDateOfBirth());

        AccountModel accountModel = new AccountModel();
        accountModel.setUsername(request.getUsername());
        accountModel.setPassword(passwordHash);
        accountModel.setFullName(request.getFullName());
        accountModel.setDateOfBirth(dateOfBirth);
        accountModel.setSex(request.getSex());

        accountRepository.save(accountModel);

        return ErrorMessage.SUCCESS;
    }
}

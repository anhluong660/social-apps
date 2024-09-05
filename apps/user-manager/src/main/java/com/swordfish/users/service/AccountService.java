package com.swordfish.users.service;

import com.swordfish.users.dto.AccountDto;
import com.swordfish.users.model.AccountDO;
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

    public ErrorMessage register(AccountDto accountDto) {
        if (accountRepository.existsByUsername(accountDto.getUsername())) {
            return ErrorMessage.ACCOUNT_EXIST;
        }

        byte[] passwordBytes = accountDto.getPassword().getBytes(StandardCharsets.UTF_8);
        String passwordHash = DigestUtils.md5DigestAsHex(passwordBytes);

        LocalDate dateOfBirth = LocalDate.parse(accountDto.getDateOfBirth());

        AccountDO accountDO = new AccountDO();
        accountDO.setUsername(accountDto.getUsername());
        accountDO.setPassword(passwordHash);
        accountDO.setFullName(accountDto.getFullName());
        accountDO.setDateOfBirth(dateOfBirth);
        accountDO.setSex(accountDto.getSex());

        accountRepository.save(accountDO);

        return ErrorMessage.SUCCESS;
    }
}

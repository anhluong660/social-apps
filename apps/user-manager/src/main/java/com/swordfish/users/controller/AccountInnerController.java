package com.swordfish.users.controller;

import com.swordfish.users.dto.entities.AccountDto;
import com.swordfish.users.service.AccountService;
import com.swordfish.utils.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inner")
public class AccountInnerController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/account")
    public AccountDto getAccountInfo(@RequestBody String token) {
        try {
            return accountService.getAccountInfo(token);
        } catch (Exception e) {
            AccountDto accountDto = new AccountDto();
            accountDto.setError(ErrorCode.AUTHENTICATE_ERROR);
            return accountDto;
        }
    }
}

package com.swordfish.users.controller;

import com.swordfish.users.dto.entities.AccountDto;
import com.swordfish.users.service.AccountService;
import com.swordfish.utils.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inner")
public class AccountInnerController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/account")
    public AccountDto getAccountInfo(String token) {
        try {
            System.out.println("Get /account");
            return accountService.getAccountInfo(token);
        } catch (Exception e) {
            AccountDto accountDto = new AccountDto();
            accountDto.setError(ErrorCode.AUTHENTICATE_ERROR);
            return accountDto;
        }
    }

    @PostMapping("/account")
    public AccountDto getAccountInfo2(String token) {
        try {
            System.out.println("Post /account");
            return accountService.getAccountInfo(token);
        } catch (Exception e) {
            AccountDto accountDto = new AccountDto();
            accountDto.setError(ErrorCode.AUTHENTICATE_ERROR);
            return accountDto;
        }
    }
}

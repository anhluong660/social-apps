package com.swordfish.users.controller;

import com.swordfish.users.dto.request.RequestRegister;
import com.swordfish.users.service.AccountService;
import com.swordfish.utils.dto.GeneralResponse;
import com.swordfish.utils.enums.ErrorMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-manager")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public GeneralResponse<?> register(@Valid @RequestBody RequestRegister request) {
        ErrorMessage error = accountService.register(request);
        return GeneralResponse.of(error);
    }
}

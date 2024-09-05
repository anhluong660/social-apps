package com.swordfish.users.controller;

import com.swordfish.users.dto.entities.LoginResult;
import com.swordfish.users.dto.request.RequestLogin;
import com.swordfish.users.dto.request.RequestRegister;
import com.swordfish.users.dto.response.ResponseLogin;
import com.swordfish.users.service.AccountService;
import com.swordfish.utils.dto.GeneralResponse;
import com.swordfish.utils.enums.ErrorMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public GeneralResponse<?> register(@Valid @RequestBody RequestRegister request) {
        ErrorMessage error = accountService.register(request);
        return GeneralResponse.of(error);
    }

    @PostMapping("/login")
    public GeneralResponse<ResponseLogin> login(@Valid @RequestBody RequestLogin request) {
        LoginResult loginResult = accountService.login(request);

        if (loginResult.getMessage() == ErrorMessage.SUCCESS) {
             ResponseLogin responseLogin = new ResponseLogin();
             responseLogin.setToken(loginResult.getToken());
             return GeneralResponse.success(responseLogin);
        } else {
            return GeneralResponse.of(loginResult.getMessage());
        }
    }
}

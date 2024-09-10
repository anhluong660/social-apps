package com.swordfish.users.controller;

import com.swordfish.users.dto.response.ResUserInfo;
import com.swordfish.users.service.UserManagerService;
import com.swordfish.utils.dto.GeneralResponse;
import com.swordfish.utils.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserManagerController {

    @Autowired
    private UserManagerService userManagerService;

    @GetMapping("/user-info")
    public GeneralResponse<ResUserInfo> getUserInfo(@RequestHeader("userId") Long userId) {
        ResUserInfo resUserInfo = userManagerService.getUserInfo(userId);
        if (resUserInfo == null) {
            return GeneralResponse.of(ErrorCode.USER_INFO_NULL);
        }

        return GeneralResponse.success(resUserInfo);
    }
}

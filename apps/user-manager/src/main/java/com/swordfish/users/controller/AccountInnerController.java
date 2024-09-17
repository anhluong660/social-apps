package com.swordfish.users.controller;

import com.swordfish.users.dto.entities.AccountDto;
import com.swordfish.users.dto.entities.UserDto;
import com.swordfish.users.service.AccountService;
import com.swordfish.users.service.UserManagerService;
import com.swordfish.utils.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inner")
public class AccountInnerController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserManagerService userManagerService;

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

    @RequestMapping("/user-info/{userId}")
    public UserDto getUserInfo(@PathVariable Long userId) {
        return userManagerService.getUserDtoInfo(userId);
    }

    @RequestMapping("/user-info-list")
    public List<UserDto> getUserInfoList(@RequestBody List<Long> userIds) {
        return userManagerService.getUserDtoInfoList(userIds);
    }

    @RequestMapping("/friendId-list")
    public List<Long> getFriendIdList(@RequestParam Long userId) {
        return userManagerService.getFriendIdList(userId);
    }

    @RequestMapping("/all-userId-list")
    public List<Long> getAllUserIdList() {
        return userManagerService.getAllUserIdList();
    }
}

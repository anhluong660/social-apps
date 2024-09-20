package com.swordfish.messenger.integration.users;

import com.swordfish.messenger.integration.users.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "user-manager")
public interface UserManagerFeign {

    @RequestMapping(value = "/inner/account")
    AccountDto getAccountInfo(String token);

    @RequestMapping("/inner/friendId-list")
    List<Long> getFriendIdList(@RequestParam Long userId);
}

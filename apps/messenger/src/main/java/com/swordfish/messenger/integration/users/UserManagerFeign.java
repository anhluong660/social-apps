package com.swordfish.messenger.integration.users;

import com.swordfish.messenger.integration.users.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "user-manager")
public interface UserManagerFeign {

    @RequestMapping(value = "/inner/account")
    AccountDto getAccountInfo(String token);
}

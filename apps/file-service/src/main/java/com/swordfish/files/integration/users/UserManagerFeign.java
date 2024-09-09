package com.swordfish.files.integration.users;

import com.swordfish.files.integration.users.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user-manager", url = "http://localhost:9001")
public interface UserManagerFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/inner/account")
    AccountDto getAccountInfo(String token);
}

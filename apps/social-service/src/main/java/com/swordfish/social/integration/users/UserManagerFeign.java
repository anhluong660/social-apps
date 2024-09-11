package com.swordfish.social.integration.users;

import com.swordfish.social.integration.users.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "user-manager", url = "http://localhost:9001")
public interface UserManagerFeign {

    @RequestMapping("/inner/user-info/{userId}")
    UserDto getUserInfo(@PathVariable("userId") Long userId);
}

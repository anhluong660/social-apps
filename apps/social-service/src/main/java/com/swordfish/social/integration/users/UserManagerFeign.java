package com.swordfish.social.integration.users;

import com.swordfish.social.integration.users.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "user-manager")
public interface UserManagerFeign {

    @RequestMapping("/inner/user-info/{userId}")
    UserDto getUserInfo(@PathVariable("userId") Long userId);

    @RequestMapping("/inner/user-info-list")
    List<UserDto> getUserInfoList(@RequestBody List<Long> userIds);

    @RequestMapping("/inner/friendId-list")
    List<Long> getFriendIdList(@RequestParam Long userId);

    @RequestMapping("/inner/all-userId-list")
    List<Long> getAllUserIdList();
}

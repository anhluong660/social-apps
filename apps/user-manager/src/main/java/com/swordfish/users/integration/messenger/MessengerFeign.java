package com.swordfish.users.integration.messenger;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@FeignClient("messenger")
public interface MessengerFeign {

    @RequestMapping("/inner/user-list/status")
    Map<Long, Boolean> getOnlineStatusByUserIdList(@RequestBody List<Long> userIdList);
}

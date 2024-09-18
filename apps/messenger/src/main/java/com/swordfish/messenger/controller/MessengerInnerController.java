package com.swordfish.messenger.controller;

import com.swordfish.messenger.service.MessengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inner")
public class MessengerInnerController {

    @Autowired
    private MessengerService messengerService;

    @RequestMapping("/user-list/status")
    public Map<Long, Boolean> getOnlineStatusByUserIdList(@RequestBody List<Long> userIdList) {
        return messengerService.getOnlineStatusByUserIdList(userIdList);
    }
}

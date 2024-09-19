package com.swordfish.messenger.controller;

import com.swordfish.messenger.dto.entities.MessageDto;
import com.swordfish.messenger.service.MessengerService;
import com.swordfish.messenger.utils.MessengerUtils;
import com.swordfish.utils.dto.GeneralPageResponse;
import com.swordfish.utils.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessengerController {

    @Autowired
    private MessengerUtils messengerUtils;

    @Autowired
    private MessengerService messengerService;

    @GetMapping("/chat-box")
    public GeneralPageResponse<MessageDto> getMessageChatBox(@RequestParam String chatBoxId, @RequestParam Integer page) {
        if (page == null || page <= 0 || !messengerUtils.checkValidChatBoxId(chatBoxId)) {
            return GeneralPageResponse.of(ErrorCode.PARAMS_INVALID);
        }

        if (!messengerService.existsChatBox(chatBoxId)) {
            return GeneralPageResponse.of(ErrorCode.NOT_FOUND);
        }

        return messengerService.getMessageChatBox(chatBoxId, page);
    }
}

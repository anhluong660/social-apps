package com.swordfish.messenger.controller;

import com.swordfish.messenger.dto.entities.MessageDto;
import com.swordfish.messenger.dto.request.ReqCreateGroupChat;
import com.swordfish.messenger.dto.response.ResChatBox;
import com.swordfish.messenger.dto.response.ResCreateGroupChat;
import com.swordfish.messenger.dto.response.ResGroupChat;
import com.swordfish.messenger.service.MessengerService;
import com.swordfish.messenger.utils.MessengerUtils;
import com.swordfish.messenger.utils.ValidatorUtils;
import com.swordfish.utils.dto.GeneralListResponse;
import com.swordfish.utils.dto.GeneralPageResponse;
import com.swordfish.utils.dto.GeneralResponse;
import com.swordfish.utils.enums.ErrorCode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class MessengerController {

    @Autowired
    private ValidatorUtils validatorUtils;

    @Autowired
    private MessengerUtils messengerUtils;

    @Autowired
    private MessengerService messengerService;

    @GetMapping("/chat-box-list")
    public GeneralListResponse<ResChatBox> getChatBoxList() {
        List<ResChatBox> resChatBoxList = messengerService.getChatBoxList();
        return GeneralListResponse.success(resChatBoxList);
    }

    @GetMapping("/chat-box")
    public GeneralPageResponse<MessageDto> getMessageChatBox(@RequestParam String chatBoxId, @RequestParam Integer page) {
        if (validatorUtils.invalidPage(page) || !messengerUtils.checkValidChatBoxId(chatBoxId)) {
            return GeneralPageResponse.of(ErrorCode.PARAMS_INVALID);
        }

        if (!messengerService.existsChatBox(chatBoxId)) {
            return GeneralPageResponse.of(ErrorCode.NOT_FOUND);
        }

        return messengerService.getMessageChatBox(chatBoxId, page);
    }

    @PostMapping("/new-group-chat")
    public GeneralResponse<ResCreateGroupChat> createGroupChat(@Valid @RequestBody ReqCreateGroupChat request) {
        Set<Long> memberIdList = request.getMemberIdList();
        ErrorCode errorCode = validatorUtils.checkMemberList(memberIdList);

        if (errorCode != ErrorCode.SUCCESS) {
            return GeneralResponse.of(errorCode);
        }

        String groupChatId = messengerService.createGroupChat(request.getGroupName(), memberIdList);

        ResCreateGroupChat response = new ResCreateGroupChat();
        response.setGroupChatId(groupChatId);
        return GeneralResponse.success(response);
    }

    @GetMapping("/group-chat-list")
    public GeneralListResponse<ResGroupChat> getGroupChatList() {
        List<ResGroupChat> resGroupChatList = messengerService.getGroupChatList();
        return GeneralListResponse.success(resGroupChatList);
    }

    @GetMapping("/group-chat")
    public GeneralPageResponse<MessageDto> getMessageGroupChat(@RequestParam String groupChatId, @RequestParam Integer page) {
        if (validatorUtils.invalidPage(page) || groupChatId == null) {
            return GeneralPageResponse.of(ErrorCode.PARAMS_INVALID);
        }

        if (!messengerService.existGroupChat(groupChatId)) {
            return GeneralPageResponse.of(ErrorCode.NOT_FOUND);
        }

        return messengerService.getMessageGroupChat(groupChatId, page);
    }
}

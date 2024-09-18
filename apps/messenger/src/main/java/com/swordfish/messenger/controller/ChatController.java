package com.swordfish.messenger.controller;

import com.swordfish.messenger.dto.request.RequestMessage;
import com.swordfish.messenger.dto.response.ResponseMessage;
import com.swordfish.messenger.integration.users.dto.AccountDto;
import com.swordfish.messenger.service.ChatService;
import com.swordfish.utils.common.SwordFishUtils;
import com.swordfish.utils.dto.ResponseSocketBase;
import com.swordfish.utils.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Controller
public class ChatController extends TextWebSocketHandler {

    @Autowired
    private ChatService chatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = session.getHandshakeHeaders().getFirst("Authorization");
        if (token == null) {
            session.close(CloseStatus.NOT_ACCEPTABLE);
            return;
        }

        AccountDto accountDto = chatService.verifyToken(token);
        if (accountDto.getError() != ErrorCode.SUCCESS) {
            session.close(CloseStatus.NOT_ACCEPTABLE);
            return;
        }

        chatService.addSession(accountDto.getUserId(), session);

        log.info("Connect socket success ! UserId = {}", accountDto.getUserId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        RequestMessage request = SwordFishUtils.fromJson(message.getPayload(), RequestMessage.class);

        if (request.getCode() == null || request.getMessageType() == null || request.getContent() == null) {
            chatService.send(session, ResponseSocketBase.fail());
            return;
        }

        switch (request.getCode()) {
            case CHAT_USER -> {
                Long receiverId = request.getReceiverId();
                if (receiverId == null) {
                    chatService.send(session, ResponseSocketBase.fail());
                    return;
                }

                chatService.send(receiverId, new ResponseMessage());
            }

            case CHAT_GROUP -> {
                if (request.getChatBoxId() == null) {
                    chatService.send(session, ResponseSocketBase.fail());
                    return;
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        long userId = chatService.removeSession(session);
        log.info("Disconnect ok ! UserId = {} | StatusCode = {}", userId, status.getCode());
    }
}
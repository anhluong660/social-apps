package com.swordfish.messenger.controller;

import com.swordfish.messenger.dto.request.ReqChatBoxMessage;
import com.swordfish.messenger.dto.request.ReqGroupChatMessage;
import com.swordfish.messenger.dto.response.ResChatBoxMessage;
import com.swordfish.messenger.dto.response.ResGroupChatMessage;
import com.swordfish.messenger.enums.MessageType;
import com.swordfish.messenger.integration.users.dto.AccountDto;
import com.swordfish.messenger.service.ChatService;
import com.swordfish.messenger.utils.ValidatorUtils;
import com.swordfish.utils.common.JsonUtils;
import com.swordfish.utils.dto.RequestMessage;
import com.swordfish.utils.dto.ResponseMessage;
import com.swordfish.utils.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

@Slf4j
@Controller
public class ChatController extends TextWebSocketHandler {

    @Autowired
    private ValidatorUtils validatorUtils;

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
        RequestMessage requestMessage = JsonUtils.fromJson(message.getPayload(), RequestMessage.class);

        if (requestMessage.getCode() == null) {
            chatService.send(session, ResponseMessage.fail());
            return;
        }

        switch (requestMessage.getCode()) {
            case CHAT_USER -> {
                ReqChatBoxMessage request = JsonUtils.fromJson(message.getPayload(), ReqChatBoxMessage.class);
                Long receiverId = request.getReceiverId();
                long senderId = chatService.getUserIdFromSession(session);
                MessageType messageType = request.getMessageType();
                String content = request.getContent();

                if (validatorUtils.invalidInputChatUser(senderId, receiverId, messageType, content)) {
                    chatService.send(session, ResponseMessage.of(ErrorCode.PARAMS_INVALID));
                    return;
                }

                chatService.handleChatToUser(session, request);

                ResChatBoxMessage response = new ResChatBoxMessage();
                response.setSenderId(senderId);
                response.setMessageType(request.getMessageType());
                response.setContent(request.getContent());

                chatService.send(receiverId, response);
            }

            case CHAT_GROUP -> {
                ReqGroupChatMessage request = JsonUtils.fromJson(message.getPayload(), ReqGroupChatMessage.class);
                String groupChatId = request.getGroupChatId();
                MessageType messageType = request.getMessageType();
                String content = request.getContent();

                if (validatorUtils.invalidInputGroupChat(groupChatId, messageType, content)) {
                    chatService.send(session, ResponseMessage.of(ErrorCode.PARAMS_INVALID));
                    return;
                }

                long senderId = chatService.getUserIdFromSession(session);

                List<Long> memberIds = chatService.handleGroupChat(senderId, request);

                if (memberIds.isEmpty()) {
                    chatService.send(session, ResponseMessage.of(ErrorCode.NOT_FOUND));
                    return;
                }

                ResGroupChatMessage response = new ResGroupChatMessage();
                response.setGroupChatId(groupChatId);
                response.setSenderId(senderId);
                response.setMessageType(messageType);
                response.setContent(content);

                chatService.send(memberIds.stream()
                        .filter(memberId -> memberId != senderId)
                        .toList(),
                        response);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        chatService.removeSession(session);
        log.info("Disconnect ok ! UserId = {} | StatusCode = {}", chatService.getUserIdFromSession(session), status.getCode());
    }
}

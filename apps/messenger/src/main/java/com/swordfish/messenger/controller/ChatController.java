package com.swordfish.messenger.controller;

import com.swordfish.messenger.dto.request.RequestMessage;
import com.swordfish.messenger.dto.response.ResponseMessage;
import com.swordfish.messenger.integration.users.dto.AccountDto;
import com.swordfish.messenger.service.ChatService;
import com.swordfish.messenger.utils.ValidatorUtils;
import com.swordfish.utils.common.JsonUtils;
import com.swordfish.utils.dto.ResponseSocketBase;
import com.swordfish.utils.enums.ErrorCode;
import com.swordfish.utils.enums.SocketCode;
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
        RequestMessage request = JsonUtils.fromJson(message.getPayload(), RequestMessage.class);

        if (request.getCode() == null || request.getMessageType() == null || request.getContent() == null) {
            chatService.send(session, ResponseSocketBase.fail());
            return;
        }

        switch (request.getCode()) {
            case CHAT_USER -> {
                Long receiverId = request.getReceiverId();
                long senderId = chatService.getUserIdFromSession(session);

                if (validatorUtils.invalidInputChatUser(senderId, receiverId)) {
                    chatService.send(session, ResponseSocketBase.of(ErrorCode.PARAMS_INVALID));
                    return;
                }

                String chatBoxId = chatService.handleChatToUser(session, request);

                ResponseMessage response = new ResponseMessage();
                response.setCode(SocketCode.CHAT_USER);
                response.setChatBoxId(chatBoxId);
                response.setSenderId(senderId);
                response.setMessageType(request.getMessageType());
                response.setContent(request.getContent());

                chatService.send(receiverId, response);
            }

            case CHAT_GROUP -> {
                if (request.getChatBoxId() == null) {
                    chatService.send(session, ResponseSocketBase.of(ErrorCode.PARAMS_INVALID));
                    return;
                }

                long senderId = chatService.getUserIdFromSession(session);

                List<Long> memberIds = chatService.handleGroupChat(senderId, request);

                if (memberIds.isEmpty()) {
                    chatService.send(session, ResponseSocketBase.of(ErrorCode.NOT_FOUND));
                    return;
                }

                ResponseMessage response = new ResponseMessage();
                response.setCode(SocketCode.CHAT_GROUP);
                response.setChatBoxId(request.getChatBoxId());
                response.setSenderId(senderId);
                response.setMessageType(request.getMessageType());
                response.setContent(request.getContent());

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

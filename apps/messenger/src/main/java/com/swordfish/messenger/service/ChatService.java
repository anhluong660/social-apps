package com.swordfish.messenger.service;

import com.swordfish.messenger.dto.request.RequestMessage;
import com.swordfish.messenger.enums.SessionProperty;
import com.swordfish.messenger.integration.users.UserManagerFeign;
import com.swordfish.messenger.integration.users.dto.AccountDto;
import com.swordfish.messenger.model.ChatBoxModel;
import com.swordfish.messenger.model.MessageModel;
import com.swordfish.messenger.repository.ChatBoxRepository;
import com.swordfish.messenger.utils.MessengerUtils;
import com.swordfish.messenger.utils.SocketManager;
import com.swordfish.utils.common.SwordFishUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private UserManagerFeign userManagerFeign;

    @Autowired
    private SocketManager socketManager;

    @Autowired
    private MessengerUtils messengerUtils;

    @Autowired
    private ChatBoxRepository chatBoxRepository;

    public AccountDto verifyToken(String token) {
        return userManagerFeign.getAccountInfo(token);
    }

    public void addSession(long userId, WebSocketSession session) {
        socketManager.addSession(userId, session);
        session.getAttributes().put(SessionProperty.USER_ID, userId);
    }

    public long getUserIdFromSession(WebSocketSession session) {
        Object userId = session.getAttributes().get(SessionProperty.USER_ID);
        return userId == null ? -1 : (long) userId;
    }

    public void removeSession(WebSocketSession session) {
        long uId = this.getUserIdFromSession(session);
        if (uId > 0) {
            socketManager.removeSession(uId);
        }
    }

    public void send(WebSocketSession session, Object response) {
        try {
            socketManager.send(session, response);
        } catch (IOException ex) {
            log.error("Exception ChatService.send(session)", ex);
        }
    }

    public void send(long userId, Object response) {
        try {
            socketManager.send(userId, response);
        } catch (Exception ex) {
            log.error("Exception ChatServer.send(userId)", ex);
        }
    }

    @SuppressWarnings("unchecked")
    public String handleChatToUser(WebSocketSession session, RequestMessage requestMessage) {
        Set<String> chatBoxSet = (Set<String>) session.getAttributes().get(SessionProperty.CHAT_BOX);
        if (chatBoxSet == null) {
            chatBoxSet = new HashSet<>();
            session.getAttributes().put(SessionProperty.CHAT_BOX, chatBoxSet);
        }

        long userId = this.getUserIdFromSession(session);
        long receiverId = requestMessage.getReceiverId();
        String chatBoxId = messengerUtils.createChatBoxId(userId, receiverId);

        if (!chatBoxSet.contains(chatBoxId)) {
            if (!chatBoxRepository.existsByChatBoxId(chatBoxId)) {
                ChatBoxModel chatBoxModel = new ChatBoxModel();
                chatBoxModel.setChatBoxId(chatBoxId);
                chatBoxModel.setMessageList(new ArrayList<>());
                chatBoxRepository.save(chatBoxModel);
            }

            chatBoxSet.add(chatBoxId);
        }

        MessageModel messageModel = new MessageModel();
        messageModel.setAuthorId(userId);
        messageModel.setType(requestMessage.getMessageType());
        messageModel.setContent(requestMessage.getContent());
        messageModel.setCreateTime(SwordFishUtils.nowUTC());

        chatBoxRepository.findAndPushMessageByChatBoxId(chatBoxId, messageModel);

        return chatBoxId;
    }


















}

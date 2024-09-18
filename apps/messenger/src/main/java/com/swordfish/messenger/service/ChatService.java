package com.swordfish.messenger.service;

import com.swordfish.messenger.enums.SessionProperty;
import com.swordfish.messenger.integration.users.UserManagerFeign;
import com.swordfish.messenger.integration.users.dto.AccountDto;
import com.swordfish.messenger.utils.SocketManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private UserManagerFeign userManagerFeign;

    @Autowired
    private SocketManager socketManager;

    public AccountDto verifyToken(String token) {
        return userManagerFeign.getAccountInfo(token);
    }

    public void addSession(long userId, WebSocketSession session) {
        socketManager.addSession(userId, session);
        session.getAttributes().put(SessionProperty.USER_ID, userId);
    }

    public long removeSession(WebSocketSession session) {
        Object userId = session.getAttributes().get(SessionProperty.USER_ID);
        if (userId == null) {
            return -1;
        }

        long uId = (long) userId;

        socketManager.removeSession(uId);
        return uId;
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

}

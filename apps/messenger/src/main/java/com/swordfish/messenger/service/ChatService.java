package com.swordfish.messenger.service;

import com.swordfish.messenger.enums.SessionProperty;
import com.swordfish.messenger.integration.users.UserManagerFeign;
import com.swordfish.messenger.integration.users.dto.AccountDto;
import com.swordfish.messenger.repository.UserRepoCustom;
import com.swordfish.messenger.utils.SocketManager;
import com.swordfish.utils.entities.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private UserManagerFeign userManagerFeign;

    @Autowired
    private SocketManager socketManager;

    @Autowired
    private UserRepoCustom userRepoCustom;

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
    private Map<Long, String> loadUserChatBox(long userId, Map<String, Object> sessionProperties) {
        Map<Long, String> chatBoxMap = (Map<Long, String>) sessionProperties.get(SessionProperty.CHAT_BOX);

        if (chatBoxMap == null) {
            List<String> chatBoxList = userRepoCustom.getChatBoxListByUserId(userId);

            chatBoxMap = userRepoCustom.getUserIdListByChatBoxList(chatBoxList)
                    .entrySet().stream()
                    .map(entry -> {
                        List<Long> memberList = entry.getValue();
                        memberList.remove(userId);
                        return memberList.isEmpty() ? null : new Pair<>(memberList.get(0), entry.getKey());
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(
                            Pair::getFirst,
                            Pair::getSecond,
                            (o, n) -> n,
                            HashMap::new
                    ));
        }

        return chatBoxMap;
    }

    public String handleChatToUser(WebSocketSession session, long receiverId) {
        long userId = this.getUserIdFromSession(session);
        Map<String, Object> sessionProperties = session.getAttributes();
        Map<Long, String> chatBoxMap = this.loadUserChatBox(userId, sessionProperties);

        String chatBoxId = chatBoxMap.get(receiverId);

        return chatBoxId;
    }


















}

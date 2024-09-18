package com.swordfish.messenger.utils;

import com.swordfish.utils.common.SwordFishUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SocketManager {

    private final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void addSession(long userId, WebSocketSession session) {
        sessions.put(userId, session);
    }

    public void removeSession(long userId) {
        sessions.remove(userId);
    }

    public boolean isOnline(long userId) {
        return sessions.containsKey(userId);
    }

    public void send(WebSocketSession session, Object response) throws IOException {
        String jsonRes = SwordFishUtils.toJson(response);
        session.sendMessage(new TextMessage(jsonRes));
    }

    public void send(long userId, Object response) throws Exception {
        WebSocketSession session = sessions.get(userId);
        if (session == null) {
            return;
        }

        send(session, response);
    }

}

package com.swordfish.messenger.utils;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SessionPropertyUtils {

    private static final String USER_ID = "UserId";
    private static final String CHAT_BOX = "ChatBox";

    private final Map<String, Object> properties;

    public SessionPropertyUtils(Map<String, Object> properties) {
        this.properties = properties;
    }

    public long getUserId() {
        return (long) properties.getOrDefault(USER_ID, -1L);
    }

    public void setUserId(long userId) {
        properties.put(USER_ID, userId);
    }

    @SuppressWarnings("unchecked")
    public Set<String> getChatBoxSet() {
        return (Set<String>) properties.computeIfAbsent(CHAT_BOX, k -> new HashSet<>());
    }

    public static SessionPropertyUtils of(WebSocketSession session) {
        return new SessionPropertyUtils(session.getAttributes());
    }
}

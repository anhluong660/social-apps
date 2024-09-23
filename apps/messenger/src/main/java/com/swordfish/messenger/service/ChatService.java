package com.swordfish.messenger.service;

import com.swordfish.messenger.dto.request.ReqChatBoxMessage;
import com.swordfish.messenger.dto.request.ReqGroupChatMessage;
import com.swordfish.messenger.integration.users.UserManagerFeign;
import com.swordfish.messenger.integration.users.dto.AccountDto;
import com.swordfish.messenger.model.ChatBoxModel;
import com.swordfish.messenger.model.MessageModel;
import com.swordfish.messenger.repository.ChatBoxRepository;
import com.swordfish.messenger.repository.GroupChatRepository;
import com.swordfish.messenger.utils.MessengerUtils;
import com.swordfish.messenger.utils.SessionPropertyUtils;
import com.swordfish.messenger.utils.SocketManager;
import com.swordfish.utils.common.DateUtil;
import com.swordfish.utils.enums.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private UserManagerFeign userManagerFeign;

    @Autowired
    private SocketManager socketManager;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private MessengerUtils messengerUtils;

    @Autowired
    private ChatBoxRepository chatBoxRepository;

    @Autowired
    private GroupChatRepository groupChatRepository;

    public AccountDto verifyToken(String token) {
        return userManagerFeign.getAccountInfo(token);
    }

    public void addSession(long userId, WebSocketSession session) {
        socketManager.addSession(userId, session);
        SessionPropertyUtils.of(session).setUserId(userId);
    }

    public long getUserIdFromSession(WebSocketSession session) {
        return SessionPropertyUtils.of(session).getUserId();
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

    public void send(List<Long> userIds, Object response) {
        for (long userId : userIds) {
            send(userId, response);
        }
    }

    public void handleChatToUser(WebSocketSession session, ReqChatBoxMessage requestMessage) {
        Set<String> chatBoxSet = SessionPropertyUtils.of(session).getChatBoxSet();

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
        messageModel.setCreateTime(DateUtil.nowUTC());

        chatBoxRepository.findAndPushMessageByChatBoxId(chatBoxId, messageModel);
    }

    public List<Long> handleGroupChat(long senderId, ReqGroupChatMessage request) {
        String groupChatId = request.getGroupChatId();
        final String GROUP_CHAT_KEY = String.format(RedisKey.GROUP_CHAT, groupChatId);

        RList<Long> memberIdsCache = redissonClient.getList(GROUP_CHAT_KEY);
        List<Long> memberIds = memberIdsCache
                .readAll()
                .stream()
                .toList();

        if (memberIds.isEmpty()) {
            memberIds = groupChatRepository.getMemberIdsByGroupChatId(groupChatId);

            if (memberIds.isEmpty()) {
                return Collections.emptyList();
            }

            memberIdsCache.addAll(memberIds);
            memberIdsCache.expire(Duration.ofHours(1));
        }

        MessageModel messageModel = new MessageModel();
        messageModel.setAuthorId(senderId);
        messageModel.setType(request.getMessageType());
        messageModel.setContent(request.getContent());
        messageModel.setCreateTime(DateUtil.nowUTC());

        groupChatRepository.findAndPushMessageByChatBoxId(groupChatId, messageModel);

        return memberIds;
    }


}

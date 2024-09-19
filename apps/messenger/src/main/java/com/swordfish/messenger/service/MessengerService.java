package com.swordfish.messenger.service;

import com.swordfish.messenger.dto.entities.MessageDto;
import com.swordfish.messenger.model.MessageModel;
import com.swordfish.messenger.repository.ChatBoxRepository;
import com.swordfish.messenger.utils.SessionPropertyUtils;
import com.swordfish.messenger.utils.SocketManager;
import com.swordfish.utils.common.RequestContextUtil;
import com.swordfish.utils.dto.GeneralPageResponse;
import com.swordfish.utils.enums.ErrorCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessengerService {

    @Autowired
    private SocketManager socketManager;

    @Autowired
    private ChatBoxRepository chatBoxRepository;

    public Map<Long, Boolean> getOnlineStatusByUserIdList(List<Long> userIdList) {
        return userIdList.stream()
                .collect(Collectors.toMap(
                        userId -> userId,
                        userId -> socketManager.isOnline(userId),
                        (oldValue, newValue) -> oldValue,
                        HashMap::new
                ));
    }

    public boolean existsChatBox(String chatBoxId) {
        long userId = RequestContextUtil.getUserId();
        WebSocketSession session = socketManager.getSession(userId);

        if (session == null) {
            return chatBoxRepository.existsByChatBoxId(chatBoxId);
        }

        Set<String> chatBoxSet = SessionPropertyUtils.of(session).getChatBoxSet();
        if (chatBoxSet.contains(chatBoxId)) {
            return true;
        }

        if (chatBoxRepository.existsByChatBoxId(chatBoxId)) {
            chatBoxSet.add(chatBoxId);
            return true;
        }

        return false;
    }

    public GeneralPageResponse<MessageDto> getMessageChatBox(String chatBoxId, int page) {
        final int PAGE_SIZE = 3;

        int totalMessage = chatBoxRepository.countMessage(chatBoxId);

        int offset = Math.max(totalMessage - PAGE_SIZE * page, 0);
        int size = Math.max(Math.min(PAGE_SIZE, totalMessage - PAGE_SIZE * (page - 1)), 0);

        List<MessageModel> messageModelList = chatBoxRepository.getMessageList(chatBoxId, offset, size);

        List<MessageDto> messageList = messageModelList.stream()
                .map(model -> {
                    MessageDto messageDto = new MessageDto();
                    BeanUtils.copyProperties(model, messageDto);
                    return messageDto;
                }).toList();

        GeneralPageResponse<MessageDto> response = new GeneralPageResponse<>();
        response.setError(ErrorCode.SUCCESS);
        response.setCurrentPage(page);
        response.setPageSize(messageList.size());
        response.setTotal(totalMessage);
        response.setList(messageList);

        return response;
    }
}

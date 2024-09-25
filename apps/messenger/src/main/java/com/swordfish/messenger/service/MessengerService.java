package com.swordfish.messenger.service;

import com.swordfish.messenger.dto.entities.MessageDto;
import com.swordfish.messenger.dto.response.ResChatBox;
import com.swordfish.messenger.dto.response.ResGroupChat;
import com.swordfish.messenger.model.GroupChatModel;
import com.swordfish.messenger.model.MessageModel;
import com.swordfish.messenger.repository.ChatBoxRepository;
import com.swordfish.messenger.repository.GroupChatRepository;
import com.swordfish.messenger.utils.MessengerUtils;
import com.swordfish.messenger.utils.MetricUtils;
import com.swordfish.messenger.utils.SessionPropertyUtils;
import com.swordfish.messenger.utils.SocketManager;
import com.swordfish.utils.common.RequestContextUtil;
import com.swordfish.utils.dto.GeneralPageResponse;
import com.swordfish.utils.enums.ErrorCode;
import com.swordfish.utils.enums.MetricAction;
import com.swordfish.utils.enums.RedisKey;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MessengerService {

    @Autowired
    private SocketManager socketManager;

    @Autowired
    private ChatBoxRepository chatBoxRepository;

    @Autowired
    private GroupChatRepository groupChatRepository;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private MessengerUtils messengerUtils;

    @Autowired
    private MetricUtils metricUtils;

    public Map<Long, Boolean> getOnlineStatusByUserIdList(List<Long> userIdList) {
        return userIdList.stream()
                .collect(Collectors.toMap(
                        userId -> userId,
                        userId -> socketManager.isOnline(userId),
                        (oldValue, newValue) -> oldValue,
                        HashMap::new
                ));
    }

    public List<ResChatBox> getChatBoxList() {
        long userId = RequestContextUtil.getUserId();
        return chatBoxRepository.findChatBoxByMemberId(userId).stream()
                .map(model -> {
                    ResChatBox response = new ResChatBox();
                    response.setChatBoxId(model.getChatBoxId());
                    response.setReceiverId(messengerUtils.getFriendByChatBoxId(model.getChatBoxId()));
                    return response;
                })
                .filter(res -> res.getReceiverId() != null)
                .toList();
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
        final int PAGE_SIZE = 20;

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

    public String createGroupChat(String groupName, Set<Long> memberIdList) {
        List<Long> memberList = new ArrayList<>(memberIdList);
        memberList.add(RequestContextUtil.getUserId());

        String groupChatId = UUID.randomUUID().toString();

        GroupChatModel groupChatModel = new GroupChatModel();
        groupChatModel.setGroupChatId(groupChatId);
        groupChatModel.setName(groupName);
        groupChatModel.setMemberList(memberList);
        groupChatModel.setMessageList(new ArrayList<>());

        groupChatRepository.save(groupChatModel);

        metricUtils.log(MetricAction.CREATE_GROUP, groupChatId, groupName, memberIdList);
        return groupChatId;
    }

    public List<ResGroupChat> getGroupChatList() {
        long userId = RequestContextUtil.getUserId();
        return groupChatRepository.findGroupListByMemberId(userId).stream()
                .map(model -> {
                    ResGroupChat response = new ResGroupChat();
                    BeanUtils.copyProperties(model, response);
                    return response;
                })
                .toList();
    }

    public boolean existGroupChat(String groupChatId) {
        final String GROUP_CHAT_KEY = String.format(RedisKey.GROUP_CHAT, groupChatId);

        if (redissonClient.getBucket(GROUP_CHAT_KEY).isExists()) {
            return true;
        }

        return groupChatRepository.existsByGroupChatId(groupChatId);
    }

    public GeneralPageResponse<MessageDto> getMessageGroupChat(String groupChatId, int page) {
        final int PAGE_SIZE = 20;

        int totalMessage = groupChatRepository.countMessage(groupChatId);

        int offset = Math.max(totalMessage - PAGE_SIZE * page, 0);
        int size = Math.max(Math.min(PAGE_SIZE, totalMessage - PAGE_SIZE * (page - 1)), 0);

        List<MessageModel> messageModelList = groupChatRepository.getMessageList(groupChatId, offset, size);

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

package com.swordfish.messenger.service;

import com.swordfish.messenger.utils.SocketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MessengerService {

    @Autowired
    private SocketManager socketManager;

    public Map<Long, Boolean> getOnlineStatusByUserIdList(List<Long> userIdList) {
        return userIdList.stream()
                .collect(Collectors.toMap(
                        userId -> userId,
                        userId -> socketManager.isOnline(userId),
                        (oldValue, newValue) -> oldValue,
                        HashMap::new
                ));
    }
}

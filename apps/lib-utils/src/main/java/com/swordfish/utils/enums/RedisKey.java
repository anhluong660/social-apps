package com.swordfish.utils.enums;

public class RedisKey {

    private static final String USERS_PREFIX = "user-manager:";
    private static final String MESSENGER = "messenger:";

    // cache userId max to generate userId
    public static final String USER_ID = USERS_PREFIX + "UserId";

    public static final String SYNC_USER_ID = USERS_PREFIX + "Sync_UserId";

    // cache member id in group chat, params[0] = GroupChatId
    public static final String GROUP_CHAT = MESSENGER + "GroupChat:%s";

}

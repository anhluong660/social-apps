package com.swordfish.utils.common;

import com.swordfish.utils.enums.HeaderName;
import com.swordfish.utils.exception.HeaderNotExistException;

import java.util.Map;

public class RequestContextUtil {

    private static final ThreadLocal<Map<HeaderName, Object>> threadLocal = new ThreadLocal<>();

    public static void setHeaders(Map<HeaderName, Object> headers) {
        threadLocal.set(headers);
    }

    public static long getUserId() {
        Object userId = threadLocal.get().get(HeaderName.USER_ID);
        if (userId == null) {
            throw new HeaderNotExistException(HeaderName.USER_ID);
        }

        return (long) userId;
    }

    public static void clear() {
        threadLocal.remove();
    }
}

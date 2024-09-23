package com.swordfish.social.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

public enum PostType {
    TEXT,
    IMAGE("jpg", "jpeg", "png"),
    VIDEO("mp4");

    private final List<String> extensions;

    PostType(String... extension) {
        this.extensions = Arrays.asList(extension);
    }

    private boolean is(String extension) {
        return this.extensions.contains(extension);
    }

    public static PostType of(String mediaLink) {
        if (StringUtils.isEmpty(mediaLink)) {
            return TEXT;
        }

        int lastIndexDot = mediaLink.lastIndexOf('.');
        String extension = mediaLink.substring(lastIndexDot + 1);

        if (IMAGE.is(extension)) {
            return IMAGE;
        }

        if (VIDEO.is(extension)) {
            return VIDEO;
        }

        return TEXT;
    }

    public static boolean isDefine(String postType) {
        for (PostType type : PostType.values()) {
            if (type.name().equals(postType)) {
                return true;
            }
        }

        return false;
    }
}

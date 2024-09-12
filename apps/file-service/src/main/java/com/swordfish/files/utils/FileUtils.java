package com.swordfish.files.utils;

import java.util.Map;

public class FileUtils {

    private static final Map<String, String> mimeTypes = Map.of(
            "image/png", "png",
            "image/jpg", "jpg",
            "image/jpeg", "jpeg",
            "audio/mp4", "mp4",
            "audio/webm", "webm"
    );

    public static String getFileType(String mimiType) {
        return mimeTypes.get(mimiType);
    }
}

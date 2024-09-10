package com.swordfish.files.utils;

import com.swordfish.utils.common.SwordFishUtils;

public interface FileUploadUtils {
    String upload(String filePath, byte[] data) throws Exception;

    static FileUploadUtils create(String className) {
        return (FileUploadUtils) SwordFishUtils.createInstance(FileUploadUtils.class.getPackageName() + "." + className);
    }
}

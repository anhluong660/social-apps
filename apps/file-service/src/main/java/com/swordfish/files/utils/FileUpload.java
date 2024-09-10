package com.swordfish.files.utils;

public interface FileUpload {
    void init();
    String upload(String filePath, byte[] data) throws Exception;
}

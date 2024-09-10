package com.swordfish.files.utils;

import org.springframework.stereotype.Component;

@Component("FileUploadCloud")
public class FileUploadCloud implements FileUpload {

    @Override
    public void init() {
        //todo...
    }

    @Override
    public String upload(String filePath, byte[] data) throws Exception {
        throw new UnsupportedOperationException();
    }
}

package com.swordfish.files.utils;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class FileUploadUtils {

    @Value("${file.upload.class}")
    private String FILE_UPLOAD_CLASS;

    @Autowired
    private Map<String, FileUpload> fileUploadMap;

    private FileUpload fileUpload;

    @PostConstruct
    private void init() {
        this.fileUpload = fileUploadMap.get(FILE_UPLOAD_CLASS);

        if (fileUpload == null) {
            throw new RuntimeException("Not found File Upload Class: " + FILE_UPLOAD_CLASS);
        } else {
            fileUpload.init();
        }
    }

    public String upload(String filePath, byte[] data) {
        try {
            return fileUpload.upload(filePath, data);
        } catch (Exception e) {
            log.error("Exception FileService.uploadFile: ", e);
            return "";
        }
    }
}

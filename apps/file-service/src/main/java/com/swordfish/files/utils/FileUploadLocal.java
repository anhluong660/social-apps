package com.swordfish.files.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component("FileUploadLocal")
public class FileUploadLocal implements FileUpload {

    @Value("${file.upload.link}")
    private String FILE_UPLOAD_LINK;

    @Override
    public void init() {
        //todo...
    }

    @Override
    public String upload(String filePath, byte[] data) throws Exception {
        Path path = Paths.get(filePath);

        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }

        Files.write(path, data);
        return FILE_UPLOAD_LINK + filePath;
    }
}

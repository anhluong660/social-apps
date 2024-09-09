package com.swordfish.files.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadLocal implements FileUploadUtils {

    @Override
    public String upload(String filePath, byte[] data) throws Exception {
        Path path = Paths.get(filePath);
        Files.write(path, data);
        return filePath;
    }
}

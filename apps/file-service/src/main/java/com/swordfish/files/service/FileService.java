package com.swordfish.files.service;

import com.swordfish.files.integration.users.UserManagerFeign;
import com.swordfish.files.integration.users.dto.AccountDto;
import com.swordfish.files.utils.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileService {

    @Autowired
    private FileUploadUtils fileUploadUtils;

    @Autowired
    private UserManagerFeign userManagerFeign;

    public String uploadFile(byte[] data, String fileType) {
        String fileName = UUID.randomUUID() + "." + fileType;
        return fileUploadUtils.upload("cloud/" + fileName, data);
    }

    public AccountDto getAccountInfo(String token) {
        return userManagerFeign.getAccountInfo(token);
    }

    public byte[] getFileLocalCloud(String fileName) {
        try {
            Path path = Paths.get("cloud/" + fileName);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            return new byte[0];
        }
    }
}

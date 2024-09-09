package com.swordfish.files.service;

import com.swordfish.files.integration.users.UserManagerFeign;
import com.swordfish.files.integration.users.dto.AccountDto;
import com.swordfish.files.utils.FileUploadCloud;
import com.swordfish.files.utils.FileUploadLocal;
import com.swordfish.files.utils.FileUploadUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class FileService {

    @Value("${server.dev-mode}")
    private boolean DEV_MODE;

    private FileUploadUtils fileUploadUtils;

    @Autowired
    private UserManagerFeign userManagerFeign;

    @PostConstruct
    public void init() {
        this.fileUploadUtils = DEV_MODE ? new FileUploadLocal() : new FileUploadCloud();
    }

    public String uploadFile(byte[] data, String fileType) {
        try {
            String fileName = UUID.randomUUID() + "." + fileType;
            return fileUploadUtils.upload("C:\\Local-Cloud\\" + fileName, data);
        } catch (Exception e) {
            log.error("Exception FileService.uploadFile: ", e);
            return "";
        }
    }

    public AccountDto getAccountInfo(String token) {
        return userManagerFeign.getAccountInfo(token);
    }
}

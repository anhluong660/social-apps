package com.swordfish.files.controller;

import com.swordfish.files.dto.request.ReqUploadFile;
import com.swordfish.files.dto.response.ResUploadFile;
import com.swordfish.files.service.FileService;
import com.swordfish.utils.dto.GeneralResponse;
import com.swordfish.utils.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public GeneralResponse<ResUploadFile> uploadFile(@RequestBody ReqUploadFile fileInfo) {
        String path = fileService.uploadFile(fileInfo.getData(), fileInfo.getType());

        if (path.isEmpty()) {
            return GeneralResponse.of(ErrorCode.FAIL);
        }

        ResUploadFile response = new ResUploadFile();
        response.setUrl(path);
        return GeneralResponse.success(response);
    }
}

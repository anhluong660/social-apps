package com.swordfish.files.controller;

import com.swordfish.files.dto.response.ResUploadFile;
import com.swordfish.files.integration.users.dto.AccountDto;
import com.swordfish.files.service.FileService;
import com.swordfish.files.utils.FileUtils;
import com.swordfish.utils.dto.GeneralResponse;
import com.swordfish.utils.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public GeneralResponse<ResUploadFile> uploadFile(@RequestHeader("Authorization") String token,
                                                     @RequestHeader("Content-Type") String mimeType,
                                                     @RequestBody byte[] file) {
        if (StringUtils.isEmpty(token)) {
            return GeneralResponse.of(ErrorCode.AUTHENTICATE_ERROR);
        }

        AccountDto accountDto = fileService.getAccountInfo(token);
        if (accountDto.getError() != ErrorCode.SUCCESS) {
            return GeneralResponse.of(accountDto.getError());
        }

        String fileType = FileUtils.getFileType(mimeType);
        if (fileType == null) {
            return GeneralResponse.of(ErrorCode.FILE_TYPE_UNSUPPORTED);
        }

        String path = fileService.uploadFile(file, fileType);

        if (path.isEmpty()) {
            return GeneralResponse.of(ErrorCode.FAIL);
        }

        ResUploadFile response = new ResUploadFile();
        response.setUrl(path);
        return GeneralResponse.success(response);
    }

    @GetMapping("/cloud/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        byte[] data = fileService.getFileLocalCloud(fileName);

        if (data.length == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }

        try {
            Path path = Paths.get(fileName);
            String mimeType = Files.probeContentType(path);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(mimeType));
            headers.setCacheControl("public, max-age=3600");
            headers.setExpires(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));

            return ResponseEntity.status(HttpStatus.OK)
                    .headers(headers)
                    .body(data);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
}

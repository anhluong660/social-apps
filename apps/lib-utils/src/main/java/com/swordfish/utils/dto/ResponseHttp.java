package com.swordfish.utils.dto;

import com.swordfish.utils.common.SwordFishUtils;

import java.util.Date;


public class ResponseHttp {
    private String timestamp;
    private int status;
    private String error;
    private String path;

    public ResponseHttp(int status, String error, String path) {
        this.timestamp = SwordFishUtils.convertToUTCStr(new Date());
        this.status = status;
        this.error = error;
        this.path = path;
    }
}

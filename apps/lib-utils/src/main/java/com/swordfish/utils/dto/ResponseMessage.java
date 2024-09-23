package com.swordfish.utils.dto;

import com.swordfish.utils.enums.ErrorCode;
import com.swordfish.utils.enums.SocketCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMessage {

    private ErrorCode error;

    private SocketCode code;

    public ResponseMessage() {
        this.error = ErrorCode.SUCCESS;
    }

    public ResponseMessage(SocketCode code) {
        this.error = ErrorCode.SUCCESS;
        this.code = code;
    }

    public static ResponseMessage success() {
        return new ResponseMessage();
    }

    public static ResponseMessage fail() {
        ResponseMessage response = new ResponseMessage();;
        response.setError(ErrorCode.FAIL);
        return response;
    }

    public static ResponseMessage of(ErrorCode error) {
        ResponseMessage response = new ResponseMessage();;
        response.setError(error);
        return response;
    }
}

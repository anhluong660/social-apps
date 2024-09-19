package com.swordfish.utils.dto;

import com.swordfish.utils.enums.ErrorCode;
import com.swordfish.utils.enums.SocketCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSocketBase {

    private ErrorCode error;

    private SocketCode code;

    public ResponseSocketBase() {
        this.error = ErrorCode.SUCCESS;
    }

    public static ResponseSocketBase success() {
        return new ResponseSocketBase();
    }

    public static ResponseSocketBase fail() {
        ResponseSocketBase response = new ResponseSocketBase();;
        response.setError(ErrorCode.FAIL);
        return response;
    }

    public static ResponseSocketBase of(ErrorCode error) {
        ResponseSocketBase response = new ResponseSocketBase();;
        response.setError(error);
        return response;
    }
}

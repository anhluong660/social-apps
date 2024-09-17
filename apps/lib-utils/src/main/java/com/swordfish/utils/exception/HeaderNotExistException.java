package com.swordfish.utils.exception;

import com.swordfish.utils.enums.HeaderName;

public class HeaderNotExistException extends RuntimeException {

    public HeaderNotExistException(HeaderName headerName) {
        super("Not found header " + headerName);
    }
}

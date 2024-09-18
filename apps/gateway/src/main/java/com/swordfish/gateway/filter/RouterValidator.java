package com.swordfish.gateway.filter;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouterValidator {

    public static final List<String> openApiEndpoints= List.of(
            "/user-manager/register",
            "/user-manager/login"
    );

    public boolean isSecured(String path) {
        return openApiEndpoints
                .stream()
                .noneMatch(path::startsWith);
    }

    public boolean isBlock(String path) {
        String[] params = path.split("/");
        return params.length > 2 && params[2].equals("inner");
    }

    public boolean isWebSocket(String path) {
        String[] params = path.split("/");
        return params.length > 2 && params[2].equals("websocket");
    }
}

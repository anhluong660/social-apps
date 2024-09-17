package com.swordfish.users.filter;

import com.swordfish.utils.common.RequestContextUtil;
import com.swordfish.utils.enums.HeaderName;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class HeaderFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //todo...
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        Map<HeaderName, Object> headers = new HashMap<>();

        if (httpRequest.getHeader("UserId") != null) {
            Long userId = Long.parseLong(httpRequest.getHeader("UserId"));
            headers.put(HeaderName.USER_ID, userId);
        }

        RequestContextUtil.setHeaders(headers);

        try {
            chain.doFilter(request, response);
        } finally {
            RequestContextUtil.clear();
        }
    }

    @Override
    public void destroy() {
        //todo...
    }
}

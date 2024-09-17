package com.swordfish.social.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

@Getter
@Setter
public class RequestNewPost {

    private String content;

    private String mediaLink;

    public boolean invalid() {
        return (StringUtils.isBlank(content) && StringUtils.isBlank(mediaLink))
                || (content != null && content.length() > 2000)
                || (mediaLink != null && mediaLink.length() > 200);
    }
}

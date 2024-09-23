package com.swordfish.social.utils;

import com.swordfish.social.enums.PostType;
import org.springframework.stereotype.Component;

@Component
public class ValidatorUtils {

    public boolean invalidPage(Integer page) {
        return page == null || page <= 0;
    }

    public boolean invalidPostType(String postType) {
        return postType == null || !PostType.isDefine(postType);
    }
}

package com.swordfish.social.utils;

import com.swordfish.social.model.PostModel;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class SocialUtils {

    public int getPointOfPost(PostModel postModel, Set<Long> friendIds) {
        long createTime = postModel.getCreateTime().getTime();
        long hoursSpend = ((new Date()).getTime() - createTime) / TimeUnit.HOURS.toMillis(1);
        int pointByCreateTime = (int) Math.max(0L, 10L - hoursSpend);

        int friendPoint = friendIds.contains(postModel.getAuthorId()) ? 20 : 0;

        return postModel.getLikeCount() + pointByCreateTime + friendPoint;
    }
}

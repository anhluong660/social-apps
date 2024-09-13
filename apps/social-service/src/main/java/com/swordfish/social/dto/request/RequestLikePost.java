package com.swordfish.social.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestLikePost {

    @NotNull(message = "postId must be not null")
    private Long postId;
}

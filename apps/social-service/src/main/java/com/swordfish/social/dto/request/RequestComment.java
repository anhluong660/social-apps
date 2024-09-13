package com.swordfish.social.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestComment {

    @NotNull(message = "postId must be not null")
    private Long postId;

    @NotNull(message = "content must be not null")
    @NotBlank(message = "content must be not empty")
    @Size(max = 1000, message = "size of content must be less than 1000")
    private String content;
}

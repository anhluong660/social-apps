package com.swordfish.social.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestNewPost {

    @NotNull(message = "Content must be not null")
    private String content;

    @NotNull(message = "Media link must be not null")
    @NotBlank(message = "Media link must be not blank")
    private String mediaLink;
}

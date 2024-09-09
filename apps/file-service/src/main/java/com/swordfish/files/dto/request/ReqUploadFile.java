package com.swordfish.files.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqUploadFile {

    @NotEmpty(message = "data is empty")
    private byte[] data;

    @NotNull(message = "type is null")
    @NotBlank(message = "type is blank")
    private String type;
}

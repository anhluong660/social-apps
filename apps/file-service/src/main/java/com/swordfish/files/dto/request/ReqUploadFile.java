package com.swordfish.files.dto.request;

import com.swordfish.files.validation.annotation.FileTypeValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqUploadFile {

    @NotNull(message = "data must be not null")
    @NotEmpty(message = "data must be not empty")
    private byte[] data;

    @NotNull(message = "type must be not null")
    @NotBlank(message = "type must be not blank")
    @FileTypeValid(message = "file type is invalid")
    private String type;
}

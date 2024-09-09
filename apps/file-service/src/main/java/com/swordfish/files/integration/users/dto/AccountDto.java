package com.swordfish.files.integration.users.dto;

import com.swordfish.utils.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
    private ErrorCode error;
    private Long userId;
}

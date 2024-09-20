package com.swordfish.messenger.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ReqCreateGroupChat {

    @NotNull(message = "group name must be not null")
    @Size(min = 3, message = "group name must be greater than 2 characters")
    private String groupName;

    @NotNull(message = "member must be exists")
    @Size(min = 2, message = "Group must be greater than 2 members")
    private Set<Long> memberIdList;
}

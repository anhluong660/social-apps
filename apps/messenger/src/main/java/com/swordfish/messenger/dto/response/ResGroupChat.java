package com.swordfish.messenger.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResGroupChat {

    private String groupChatId;

    private String name;

    private List<Long> memberList;

}

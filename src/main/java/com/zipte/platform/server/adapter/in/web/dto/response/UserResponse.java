package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.user.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    /*
        닉네임, 한줄 소개
     */

    private final Long userId;
    private final String nickname;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();

    }
}

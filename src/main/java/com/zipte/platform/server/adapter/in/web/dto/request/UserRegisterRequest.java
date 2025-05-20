package com.zipte.platform.server.adapter.in.web.dto.request;

import com.zipte.platform.server.domain.user.OAuthProvider;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterRequest {

    @NotNull(message = "email은 반드시 입력해야하는 필수 사항입니다!")
    @Email(message = "이메일 형식이 적합하지 않습니다")
    private String email;

    private String socialId;

    @NotNull(message = "username은 반드시 입력해야하는 필수 사항입니다!")
    @Size(max = 10, message = "이름은 최대 10글자 입니다")
    private String username;

    @NotNull(message = "nickname은 반드시 입력해야하는 필수 사항입니다!")
    @Size(max = 20, message = "닉네임은 최대 20글자 입니다.")
    private String nickname;

    private OAuthProvider provider;

    private String imageUrl;

    @NotNull(message = "birthday는 반드시 입력해야하는 필수 사항입니다!")
    private String birthday;

    @NotNull(message = "개인 정보 동의여부는 필수 사항입니다!")
    private UserConsentRequest consent;


}

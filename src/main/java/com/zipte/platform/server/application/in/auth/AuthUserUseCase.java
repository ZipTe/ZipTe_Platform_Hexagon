package com.zipte.platform.server.application.in.auth;

import com.zipte.platform.server.adapter.in.web.dto.request.UserRegisterRequest;
import com.zipte.platform.server.domain.user.User;

public interface AuthUserUseCase {

    /*
        사용자는 카카오 OAuth를 통해 회원가입을 진행할 수 있어야 합니다.
        OAuth를 통해 이메일, 이미지, 이름을 얻어올 수 있습니다.
     */

    // 최초 로그인 시 (추가정보 사이트로 이전하여 회원가입)
    User registerUser(UserRegisterRequest request);

}

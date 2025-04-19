package com.zipte.platform.server.application.in.user;


import com.zipte.platform.server.adapter.in.web.dto.request.UserUpdateRequest;

public interface UpdateUserUseCase {

    /*
        소셜 로그인 유저의 개인정보 수정 - [이미지, 닉네임, 한줄 소개, 생년월일]에 대해 수정이 가능하다.
     */

    void updateUser(Long userId, UserUpdateRequest request);
}

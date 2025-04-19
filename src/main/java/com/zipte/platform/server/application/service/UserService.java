package com.zipte.platform.server.application.service;

import com.zipte.platform.server.adapter.in.web.dto.request.UserUpdateRequest;
import com.zipte.platform.server.application.in.user.GetUserUseCase;
import com.zipte.platform.server.application.in.user.UpdateUserUseCase;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements GetUserUseCase, UpdateUserUseCase {

    private final UserPort userPort;


    /// 조회
    @Override
    public User getMyInfo(Long userId) {

        /// 예외 처리
        return userPort.loadUserById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저는 존재하지 않습니다."));
    }


    /// 수정
    @Override
    public void updateUser(Long userId, UserUpdateRequest request) {

        User user = userPort.loadUserById(userId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저는 수정할 수 없습니다."));

        ///  카카오 및 자체 유저의 수정하기
        // 닉네임이 요청에 있으면 수정
        if (request.getNickname() != null && !request.getNickname().isEmpty()) {
            user.changeNickname(request.getNickname());
        }


        // 이미지 URL이 요청에 있으면 수정
        if (request.getImageUrl() != null && !request.getImageUrl().isEmpty()) {
            user.changeImageUrl(request.getImageUrl());
        }

        log.info(user.getNickname());

        userPort.updateUser(user);

    }




}

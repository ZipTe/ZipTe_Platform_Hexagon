package com.zipte.platform.server.application.service;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.server.adapter.in.web.dto.request.UserUpdateRequest;
import com.zipte.platform.server.application.in.user.GetUserUseCase;
import com.zipte.platform.server.application.in.user.UpdateUserUseCase;
import com.zipte.platform.server.application.out.external.image.ImagePort;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.user.User;
import com.zipte.platform.server.domain.user.UserConsent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements GetUserUseCase, UpdateUserUseCase {

    private final UserPort userPort;

    /// 이미지 의존성
    private final ImagePort imagePort;

    /// 조회
    @Override
    public User getMyInfo(Long userId) {

        /// 예외 처리
        return userPort.loadUserById(userId)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_USER.getMessage()));
    }


    /// 수정
    @Override
    public void updateUser(Long userId, UserUpdateRequest request) {

        /// 예외 처리
        User user = userPort.loadUserById(userId)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_USER.getMessage()));

        /// 수정 사항 예외처리
        if (request.getImage() != null) {
            MultipartFile image = request.getImage();

            String imageUrl = imagePort.uploadFile(image);
            user.changeImageUrl(imageUrl);
        }

        if (request.getBirthday() != null) {
            user.changeBirthDay(request.getBirthday());
        }

        if (request.getNickname() != null) {
            user.changeNickname(request.getNickname());
        }

        if (request.getConsent() != null) {
            UserConsent consent = user.getConsent();
            consent.changeConsent(request.getConsent());
        }

        /// 유저와 개인정보 한번에 업데이트!
        userPort.updateUser(user);

    }




}

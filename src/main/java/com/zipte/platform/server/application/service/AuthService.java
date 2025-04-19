package com.zipte.platform.server.application.service;

import com.zipte.platform.server.adapter.in.web.dto.request.UserRegisterRequest;
import com.zipte.platform.server.application.in.auth.AuthUserUseCase;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.application.service.exception.DuplicationUserException;
import com.zipte.platform.server.domain.user.User;
import com.zipte.platform.server.domain.user.UserConsent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements AuthUserUseCase {

    private final UserPort userPort;

    @Override
    public User registerUser(UserRegisterRequest request) {

        ///  중복 예외 처리
        boolean existing = userPort.checkExistingBySocialAndSocialId(request.getProvider(), request.getSocialId());

        if (existing){
            throw new DuplicationUserException("이미 존재하는 유저입니다.");
        }

        User user = User.of(request.getSocialId(), request.getEmail(), request.getUsername(),
                request.getNickname(), request.getImageUrl(), request.getBirthday(),
                request.getProvider(), UserConsent.of(request.getConsent()));

        return userPort.saveUser(user);
    }
}

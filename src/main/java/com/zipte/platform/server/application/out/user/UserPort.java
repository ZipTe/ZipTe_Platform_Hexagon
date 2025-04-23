package com.zipte.platform.server.application.out.user;


import com.zipte.platform.server.domain.user.OAuthProvider;
import com.zipte.platform.server.domain.user.User;

import java.util.Optional;

public interface UserPort {

    /// 저장
    User saveUser(User user);

    // 수정하기
    User updateUser(User user);

    /// 조회하기
    boolean checkExistingBySocialAndSocialId(OAuthProvider social, String socialId);

    boolean checkExistingById(Long userId);

    Optional<User> loadUserById(Long userId);

    Optional<User> loadUserBySocialAndSocialId(OAuthProvider social, String socialId);
}

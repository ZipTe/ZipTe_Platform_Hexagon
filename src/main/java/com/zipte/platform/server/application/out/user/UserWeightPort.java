package com.zipte.platform.server.application.out.user;

import com.zipte.platform.server.domain.user.UserWeight;

import java.util.Optional;

public interface UserWeightPort {

    UserWeight saveUserWeight(UserWeight userWeight);

    Optional<UserWeight> loadUserWeightByUserId(Long userId);

    void deleteUserWeight(Long weightId);

}

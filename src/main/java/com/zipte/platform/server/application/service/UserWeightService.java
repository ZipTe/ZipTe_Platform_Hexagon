package com.zipte.platform.server.application.service;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.server.adapter.in.web.dto.request.UserWeightRequest;
import com.zipte.platform.server.application.in.user.UserWeightUseCase;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.application.out.user.UserWeightPort;
import com.zipte.platform.server.domain.user.UserWeight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserWeightService implements UserWeightUseCase {

    private final UserWeightPort weightPort;

    // 의존성
    private final UserPort loadUserPort;

    @Override
    public UserWeight createWeight(UserWeightRequest request) {

        /// 유저 예외처리
        if(!loadUserPort.checkExistingById(request.getUserId())){
            throw new NoSuchElementException(ErrorCode.NOT_USER.getMessage());
        }

        /// 가중치 값이 0 미만일 경우, 예외 처리


        /// 객체 생성
        UserWeight userWeight = UserWeight.of(request.getUserId(), request.getConvenience(), request.getTransportation(),
                        request.getRegionPreference(), request.getParkAccess(), request.getDistanceToWork());

        return weightPort.saveUserWeight(userWeight);
    }

    @Override
    public UserWeight getWeight(Long userId) {

        /// 유저 예외처리
        if(!loadUserPort.checkExistingById(userId)){
            throw new NoSuchElementException(ErrorCode.NOT_USER.getMessage());
        }

        /// 결과값 생성
        return weightPort.loadUserWeightByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_WEIGHT.getMessage()));
    }

    @Override
    public void removeWeight(Long userId, Long weightId) {

        /// 유저 예외처리
        if(!loadUserPort.checkExistingById(userId)){
            throw new NoSuchElementException(ErrorCode.NOT_USER.getMessage());
        }

        /// 유저가 가중치와 같은 사람인지 예외 처리
        Optional<UserWeight> weight = weightPort.loadUserWeightByUserId(userId);
        if(weight.isPresent()){
            if (!weight.get().getId().equals(weightId)) {
                throw new NoSuchElementException(ErrorCode.NOT_WEIGHT.getMessage());
            }
        }

        weightPort.deleteUserWeight(weightId);

    }
}

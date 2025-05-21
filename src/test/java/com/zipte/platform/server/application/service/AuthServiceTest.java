package com.zipte.platform.server.application.service;

import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.application.service.exception.DuplicationUserException;
import com.zipte.platform.server.domain.user.User;
import com.zipte.platform.server.domain.user.UserFixtures;
import com.zipte.platform.server.domain.user.UserRegisterRequestFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserPort userPort;

    @InjectMocks
    private AuthService sut;


    @Nested
    @DisplayName("생성 테스트")
    class Save {

        @Test
        @DisplayName("[happy] 정상적으로 유저를 생성합니다.")
        void save() {

            // Given
            var request = UserRegisterRequestFixtures.stub_required();

            given(userPort.checkExistingBySocialAndSocialId(request.getProvider(), request.getSocialId()))
                    .willReturn(false);

            User user = UserFixtures.stub();

            given(userPort.saveUser(any()))
                    .willReturn(user);

            // When
            var result = sut.registerUser(request);

            // Then
            then(result)
                    .isNotNull()
            ;

        }

        @Test
        @DisplayName("[edge] 개인정보필수동의를 받지않은 유저를 생성합니다.")
        void save_edge_consent() {

            // Given
            var request = UserRegisterRequestFixtures.stub_false();

            given(userPort.checkExistingBySocialAndSocialId(request.getProvider(), request.getSocialId()))
                    .willReturn(false);

            // When & Then
            org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                    () -> sut.registerUser(request));

        }

        @Test
        @DisplayName("[error] 동의여부가 null인 유저를 생성합니다.")
        void save_edge_consent_null() {

            // Given
            var request = UserRegisterRequestFixtures.withoutConsent();

            given(userPort.checkExistingBySocialAndSocialId(request.getProvider(), request.getSocialId()))
                    .willReturn(false);

            // When & Then
            org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                    () -> sut.registerUser(request));

        }

        @Test
        @DisplayName("[error] 이미 존재하는 유저가 생성됩니다.")
        void save_duplicate() {

            // Given
            var request = UserRegisterRequestFixtures.stub();

            given(userPort.checkExistingBySocialAndSocialId(request.getProvider(), request.getSocialId()))
                    .willReturn(true);

            // When & Then
            org.junit.jupiter.api.Assertions.assertThrows(DuplicationUserException.class,
                    () -> sut.registerUser(request));

        }
    }

}

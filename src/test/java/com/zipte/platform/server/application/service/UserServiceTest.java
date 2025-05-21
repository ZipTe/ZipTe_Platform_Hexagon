package com.zipte.platform.server.application.service;

import com.zipte.platform.server.adapter.in.web.dto.request.UserConsentUpdateRequest;
import com.zipte.platform.server.adapter.in.web.dto.request.UserUpdateRequest;
import com.zipte.platform.server.application.out.external.image.ImagePort;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.user.User;
import com.zipte.platform.server.domain.user.UserFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserPort userPort;

    @Mock
    private ImagePort imagePort;

    @InjectMocks
    private UserService sut;


    @Nested
    @DisplayName("유저 정보 수정 테스트")
    class Save {

        @Test
        @DisplayName("[happy] 정상적으로 유저를 수정")
        void save() {
            // Given
            Long userId = 1L;
            User user = UserFixtures.stub();
            String imageUrl = "https://image.url/profile.png";


            given(userPort.loadUserById(userId))
                    .willReturn(Optional.of(user));
            given(imagePort.uploadFile(any(MockMultipartFile.class)))
                    .willReturn(imageUrl);

            var consentRequest = getUserConsentUpdateRequest();

            // 변경 요청 DTO 생성
            UserUpdateRequest request = getUserUpdateRequest(consentRequest);


            // When
            sut.updateUser(userId, request);

            // Then
            verify(userPort)
                    .updateUser(any(User.class));

            then(user.getNickname()).isEqualTo("새닉네임");
            then(user.getBirthday()).isEqualTo("1990-01-01");
            then(user.getImageUrl()).isEqualTo(imageUrl);
            then(user.getConsent().isTermsRequired()).isTrue();
            then(user.getConsent().isMarketingSMSOptional()).isTrue();
        }

        private static UserConsentUpdateRequest getUserConsentUpdateRequest() {
            var consentRequest = UserConsentUpdateRequest.builder()
                    .adsOptional(false)
                    .marketingSMSOptional(true)
                    .marketingEmailsOptional(false)
                    .dataSharingOptional(false)
                    .build();
            return consentRequest;
        }


        private static UserUpdateRequest getUserUpdateRequest(UserConsentUpdateRequest consentRequest) {
            MockMultipartFile mockImage = new MockMultipartFile(
                    "image",               // name
                    "profile.png",         // original filename
                    "image/png",           // content type
                    "dummy".getBytes()     // content
            );

            return UserUpdateRequest.builder()
                    .nickname("새닉네임")
                    .birthday("1990-01-01")
                    .consent(consentRequest)
                    .image(mockImage)
                    .build();
        }


        @Test
        @DisplayName("[edge] 유저를 수정하는 것에 실패_존재하지 않는 유저")
        void save_edge() {

            // Given
            Long userId = 99L;
            UserUpdateRequest request = UserUpdateRequest.builder()
                    .nickname("닉네임")
                    .build();

            given(userPort.loadUserById(userId))
                    .willReturn(Optional.empty());

            // When & Then
            org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
                    () -> sut.updateUser(userId, request));
        }


    }



    @Nested
    @DisplayName("정보 조회 테스트")
    class Load {

        @Test
        @DisplayName("[happy]정상 조회")
        void load_happy() {

            // Given
            Long userId = 1L;

            given(userPort.loadUserById(userId))
                    .willReturn(Optional.of(UserFixtures.stub()));
            // When
            var myInfo = sut.getMyInfo(userId);

            // Then
            then(myInfo)
                    .isNotNull()
                    .hasFieldOrPropertyWithValue("id", userId);


        }

        @Test
        @DisplayName("[error]정상 조회_유저가 존재하지 않음")
        void load_error() {

            // Given
            Long userId = 1L;

            given(userPort.loadUserById(userId))
                    .willReturn(Optional.empty());

            // When & Then
            org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
                    () -> sut.getMyInfo(userId));

        }

    }

}

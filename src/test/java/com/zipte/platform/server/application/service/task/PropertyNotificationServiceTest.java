package com.zipte.platform.server.application.service.task;

import com.zipte.platform.server.application.out.favorite.FavoritePort;
import com.zipte.platform.server.application.out.notification.property.DeletePropertyNotificationPort;
import com.zipte.platform.server.application.out.notification.property.SavePropertyNotificationPort;
import com.zipte.platform.server.domain.notification.PropertyFixtures;
import com.zipte.platform.server.domain.property.Property;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Service] 알림 생성 단위 테스트")
class PropertyNotificationServiceTest {

    @Mock
    private SavePropertyNotificationPort savePort;

    @Mock
    private DeletePropertyNotificationPort deletePort;

    @Mock
    private FavoritePort favoritePort;


    @InjectMocks
    private PropertyNotificationService sut;

    @Test
    @DisplayName("[bad] 관심 등록한 유저가 없는 경우 알림 생성되지 않음")
    void createNotification_noFavorites() {
        // Given
        String kaptCode = "A10027221";
        List<Long> users = List.of(); // 아무도 관심 등록 안함
        Property stub = PropertyFixtures.stub(1L, kaptCode);

        given(favoritePort.loadUserFavoriteByComplexCode(kaptCode))
                .willReturn(users);

        // When
        sut.createNotification(stub);

        // Then
        verify(savePort, never()).saveNotification(any());
    }


    @Test
    @DisplayName("[bad] 본인 아파트의 매물 추가에 따른 알림 생성되지 않음")
    void createNotification_bad() {

        // Given
        String kaptCode = "A10027221";
        List<Long> users = List.of(1L);
        Property stub = PropertyFixtures.stubs(1L, kaptCode);

        given(favoritePort.loadUserFavoriteByComplexCode(kaptCode))
                .willReturn(users);

        // When
        /// A10027221 매물 추가 로직
        sut.createNotification(stub);

        // Then
        verify(savePort, times(0))
                .saveNotification(any());

    }

    @Test
    @DisplayName("[happy] 아파트의 매물 추가에 따른 알림 생성")
    void createNotification_successfully() {

        // Given
        String kaptCode = "A10027221";
        List<Long> users = List.of(2L);
        Property stub = PropertyFixtures.stub(1L, kaptCode);

        given(favoritePort.loadUserFavoriteByComplexCode(kaptCode))
                .willReturn(users);

        // When
        /// A10027221 매물 추가 로직
        sut.createNotification(stub);

        // Then
        verify(savePort, times(1))
                .saveNotification(any());

    }


    @Test
    @DisplayName("[happy] 복수의 사용자에게 동일한 매물이 알림으로 전송됨")
    void createNotification_successfully_multiple() {

        // Given
        String kaptCode = "A10027221";
        List<Long> users = List.of(2L,3L);
        Property stub = PropertyFixtures.stub(1L, kaptCode);

        given(favoritePort.loadUserFavoriteByComplexCode(kaptCode))
                .willReturn(users);

        // When
        /// A10027221 매물 추가 로직
        sut.createNotification(stub);

        // Then
        verify(savePort, times(2))
                .saveNotification(any());

    }



}

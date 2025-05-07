package com.zipte.platform.server.application.service;

import com.zipte.platform.server.application.out.notification.LoadNotificationPort;
import com.zipte.platform.server.application.out.notification.TimeNotificationPort;
import com.zipte.platform.server.domain.notification.PropertyNotificationFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private TimeNotificationPort timeNotificationPort;

    @Mock
    private LoadNotificationPort loadNotificationPort;

    @InjectMocks
    private NotificationService sut;

    @Test
    @DisplayName("[happy] 알림기록 없는 경우 알림 존재 할 때, 조회 테스트")
    void checkNewNotificationByHappy() {
        // Given
        Long userId = 1L;
        String kaptCode = "kaptCode";
        var stub = PropertyNotificationFixtures.stub(kaptCode);

        /// 이전에 알림을 조회해본 시간이 X
        given(timeNotificationPort.getLatestReadAt(userId))
                .willReturn(null);

        given(loadNotificationPort.loadNotificationsAt(userId))
                .willReturn(Optional.of(stub));

        // When
        var result = sut.checkNewNotification(userId);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("[bad] 알림기록 없는 경우 알림 존재 하지 않을 때, 조회 테스트")
    void checkNewNotificationByBad() {
        // Given
        Long userId = 1L;

        given(timeNotificationPort.getLatestReadAt(userId))
                .willReturn(null);

        given(loadNotificationPort.loadNotificationsAt(userId))
                .willReturn(Optional.empty());

        // When
        var result = sut.checkNewNotification(userId);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("[happy] 알림기록 존재한 경우 알림 존재 할 때, 조회 테스트")
    void checkNewNotificationByTime() {
        // Given
        Long userId = 1L;

        /// 최근 조회한 시간 존재 (1분전)
        given(timeNotificationPort.getLatestReadAt(userId))
                .willReturn(LocalDateTime.now().minusMinutes(1));

        given(loadNotificationPort.loadNotificationsAt(userId))
                .willReturn(Optional.empty());

        // When
        var result = sut.checkNewNotification(userId);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("[bad] 알림기록 존재한 경우 알림 존재 하지 않을 때, 조회 테스트")
    void checkNewNotificationByBadTime() {
        // Given
        Long userId = 1L;

        /// 최근 조회한 시간 존재 (1분전)
        given(timeNotificationPort.getLatestReadAt(userId))
                .willReturn(LocalDateTime.now().minusMinutes(1));

        given(loadNotificationPort.loadNotificationsAt(userId))
                .willReturn(Optional.empty());

        // When
        var result = sut.checkNewNotification(userId);

        // Then
        assertFalse(result);
    }
}

package com.zipte.platform.server.application.service;

import com.zipte.platform.server.application.in.notification.UserNotificationUseCase;
import com.zipte.platform.server.application.out.notification.LoadNotificationPort;
import com.zipte.platform.server.application.out.notification.TimeNotificationPort;
import com.zipte.platform.server.domain.notification.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService implements UserNotificationUseCase {

    private final TimeNotificationPort timeNotificationPort;
    private final LoadNotificationPort loadNotificationPort;

    @Override
    public boolean checkNewNotification(Long userId) {
        LocalDateTime readAt = timeNotificationPort.getLatestReadAt(userId);
        Notification latest = loadNotificationPort.loadNotificationsAt(userId).orElse(null);

        if (latest == null) {
            return false;
        }

        log.info("Checking if notifications have been sent to user {}", userId);
        log.info("Latest read at: {}", readAt);
        log.info("Notifications at: {}", latest.getCreatedAt());

        if (readAt == null) {
            return true; // 최신 읽은 시간이 없으면 무조건 새로운 알림이 있음
        }

        return readAt.isBefore(latest.getCreatedAt()); // 새로운 알림이 있는지 체크
    }

    @Override
    public Page<Notification> loadAllByUserId(Long userId, Pageable pageable) {

        // 알림 읽음 시간을 현재로 바꾼다.
        timeNotificationPort.setLatestReadAt(userId);

        // 전체 알림을 가져온다.
        return loadNotificationPort.loadNotifications(userId, pageable);
    }

}

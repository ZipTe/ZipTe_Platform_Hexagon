package com.zipte.platform.server.application.out.notification;

import com.zipte.platform.server.domain.notification.AnswerNotification;import com.zipte.platform.server.domain.notification.Notification;
import com.zipte.platform.server.domain.notification.PropertyNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LoadNotificationPort {

    // 최신 알림 목록 조회
    Page<Notification> loadNotifications(Long userId, Pageable pageable);

    // 최신 알람의 시간
    Optional<Notification> loadNotificationsAt(Long userId);

    // 답글 알림 상세 조회
    Optional<AnswerNotification> loadAnswerNotification(Long answerId);

    // 매물 알림 상세 조회
    Optional<PropertyNotification> loadPropertyNotification(String complexCode);


}

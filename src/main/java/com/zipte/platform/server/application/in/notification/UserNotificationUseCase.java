package com.zipte.platform.server.application.in.notification;

import com.zipte.platform.server.domain.notification.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserNotificationUseCase {

    // 새로운 알림 여부
    boolean checkNewNotification(Long userId);

    // 알림 목록 가져오기
    Page<Notification> loadAllByUserId(Long userId, Pageable pageable);
}

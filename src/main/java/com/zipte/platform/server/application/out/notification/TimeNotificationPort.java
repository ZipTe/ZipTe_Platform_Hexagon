package com.zipte.platform.server.application.out.notification;

import java.time.LocalDateTime;

public interface TimeNotificationPort {

    // 현재시간으로 읽음처리
    void setLatestReadAt(Long userId);

    // 캐시에서 나의 읽음시간 가져오기
    LocalDateTime getLatestReadAt(Long userId);
}

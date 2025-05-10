package com.zipte.platform.server.domain.notification;

import java.time.LocalDateTime;

public class PropertyNotificationFixtures {
    public static PropertyNotification stub(String kaptCode) {
        return PropertyNotification.builder()
                .id("id")
                .kaptCode(kaptCode)
                .userId(2L)
                .price(100000)
                .createdAt(LocalDateTime.now())
                .occurredAt(LocalDateTime.now())
                .lastUpdatedAt(LocalDateTime.now())
                .build();
    }

}

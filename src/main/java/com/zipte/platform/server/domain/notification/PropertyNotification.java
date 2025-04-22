package com.zipte.platform.server.domain.notification;

import com.zipte.platform.server.adapter.out.mongo.notification.base.NotificationType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class PropertyNotification extends Notification {

    private String complexCode;
    private long price;

    // 생성자
    public static PropertyNotification of(String id, NotificationType type,Long userId, LocalDateTime occurredAt, LocalDateTime createdAt, LocalDateTime lastUpdatedAt, LocalDateTime deleteAt, String complexCode, long price){
        return PropertyNotification.builder()
                .id(id)
                .type(type)
                .userId(userId)
                .complexCode(complexCode)
                .price(price)
                .occurredAt(occurredAt)
                .createdAt(createdAt)
                .lastUpdatedAt(lastUpdatedAt)
                .deleteAt(deleteAt)
                .build();
    }

}

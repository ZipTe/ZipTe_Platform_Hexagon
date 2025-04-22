package com.zipte.platform.server.domain.notification;

import com.zipte.platform.server.adapter.out.mongo.notification.base.NotificationType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public abstract class Notification {

    public String id;
    public Long userId;
    public NotificationType type;
    public LocalDateTime occurredAt;
    public LocalDateTime createdAt;
    public LocalDateTime lastUpdatedAt;
    public LocalDateTime deleteAt;

}

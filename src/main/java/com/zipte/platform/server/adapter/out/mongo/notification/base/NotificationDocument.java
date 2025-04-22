package com.zipte.platform.server.adapter.out.mongo.notification.base;

import com.zipte.platform.server.domain.notification.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document("notifications")
public abstract class NotificationDocument {

    @Field(targetType = FieldType.STRING)
    private String id;

    private Long userId;
    private NotificationType type;
    private LocalDateTime occurredAt;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private LocalDateTime deletedAt;

    public abstract Notification toDomain();
}

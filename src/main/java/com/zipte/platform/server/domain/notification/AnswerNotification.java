package com.zipte.platform.server.domain.notification;

import com.zipte.platform.server.adapter.out.mongo.notification.base.NotificationType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class AnswerNotification extends Notification {

    private Long questionId;
    private Long writerId;
    private Long answerId;
    private String content;

    /// 생성잔
    public static AnswerNotification of(String id, Long userId, NotificationType type, LocalDateTime occurredAt, LocalDateTime createdAt, LocalDateTime lastUpdatedAt, LocalDateTime deleteAt, Long questionId, Long writerId, Long answerId, String content){
        return AnswerNotification.builder()
                .id(id)
                .userId(userId)
                .type(type)
                .questionId(questionId)
                .writerId(writerId)
                .answerId(answerId)
                .content(content)
                .createdAt(createdAt)
                .occurredAt(occurredAt)
                .deleteAt(deleteAt)
                .lastUpdatedAt(lastUpdatedAt)
                .build();

    }

    /// 비즈니스 로직
}

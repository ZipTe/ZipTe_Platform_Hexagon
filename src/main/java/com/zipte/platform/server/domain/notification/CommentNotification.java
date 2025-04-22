package com.zipte.platform.server.domain.notification;

import com.zipte.platform.server.adapter.out.mongo.notification.base.NotificationType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class CommentNotification extends Notification {

    private Long questionId;
    private Long writerId;
    private Long commentId;
    private String comment;

    /// 생성잔
    public static CommentNotification of(String id, Long userId, NotificationType type, LocalDateTime occurredAt, LocalDateTime createdAt, LocalDateTime lastUpdatedAt, LocalDateTime deleteAt, Long postId, Long writerId, Long commentId, String comment){
        return CommentNotification.builder()
                .id(id)
                .userId(userId)
                .type(type)
                .questionId(postId)
                .writerId(writerId)
                .commentId(commentId)
                .comment(comment)
                .createdAt(createdAt)
                .occurredAt(occurredAt)
                .deleteAt(deleteAt)
                .lastUpdatedAt(lastUpdatedAt)
                .build();

    }

    /// 비즈니스 로직
}

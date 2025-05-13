package com.zipte.platform.server.adapter.out.mongo.notification.answer;

import com.zipte.platform.server.adapter.out.mongo.notification.base.NotificationDocument;
import com.zipte.platform.server.domain.notification.AnswerNotification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;

@Getter
@TypeAlias("answer_notification")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AnswerNotificationDocument extends NotificationDocument {

    private Long questionId;
    private Long writerId;
    private Long answerId;
    private String content;

    // from
    public static AnswerNotificationDocument from(AnswerNotification notification) {
        return AnswerNotificationDocument.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .type(notification.getType())
                .occurredAt(notification.getOccurredAt())
                .createdAt(notification.getCreatedAt())
                .lastUpdatedAt(notification.getLastUpdatedAt())
                .deletedAt(notification.getDeleteAt())
                .questionId(notification.getQuestionId())
                .writerId(notification.getWriterId())
                .answerId(notification.getAnswerId())
                .content(notification.getContent())
                .build();
    }

    // toDomain
    public AnswerNotification toDomain() {
        return AnswerNotification.builder()
                .id(getId())
                .userId(getUserId())
                .type(getType())
                .occurredAt(getOccurredAt())
                .createdAt(getCreatedAt())
                .lastUpdatedAt(getLastUpdatedAt())
                .deleteAt(getDeletedAt())
                .questionId(questionId)
                .writerId(writerId)
                .answerId(answerId)
                .content(content)
                .build();
    }
}

package com.zipte.platform.server.adapter.out.mongo.notification.comment;

import com.zipte.platform.server.adapter.out.mongo.notification.base.NotificationDocument;
import com.zipte.platform.server.domain.notification.CommentNotification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;

@Getter
@TypeAlias("comment_notification")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CommentDocument extends NotificationDocument {

    private Long postId;
    private Long writerId;
    private Long commentId;
    private String comment;

    // from
    public static CommentDocument from(CommentNotification notification) {
        return CommentDocument.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .type(notification.getType())
                .occurredAt(notification.getOccurredAt())
                .createdAt(notification.getCreatedAt())
                .lastUpdatedAt(notification.getLastUpdatedAt())
                .deletedAt(notification.getDeleteAt())
                .postId(notification.getPostId())
                .writerId(notification.getWriterId())
                .commentId(notification.getCommentId())
                .comment(notification.getComment())
                .build();
    }

    // toDomain
    public CommentNotification toDomain() {
        return CommentNotification.builder()
                .id(getId())
                .userId(getUserId())
                .type(getType())
                .occurredAt(getOccurredAt())
                .createdAt(getCreatedAt())
                .lastUpdatedAt(getLastUpdatedAt())
                .deleteAt(getDeletedAt())
                .postId(this.postId)
                .writerId(this.writerId)
                .commentId(this.commentId)
                .comment(this.comment)
                .build();
    }
}

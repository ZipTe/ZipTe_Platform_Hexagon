package com.zipte.platform.server.application.service.task;

import com.zipte.platform.core.util.NotificationIdGenerator;
import com.zipte.platform.server.adapter.out.mongo.notification.base.NotificationType;
import com.zipte.platform.server.application.in.task.CommentNotificationTask;
import com.zipte.platform.server.application.out.notification.comment.DeleteCommentNotificationPort;
import com.zipte.platform.server.application.out.notification.comment.SaveCommentNotificationPort;
import com.zipte.platform.server.domain.community.comment.Comment;
import com.zipte.platform.server.domain.notification.CommentNotification;import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentNotificationService implements CommentNotificationTask {

    private final SaveCommentNotificationPort savePort;
    private final DeleteCommentNotificationPort deletePort;

    @Override
    public void createNotification(Comment comment) {

        LocalDateTime now = LocalDateTime.now();

        // 글 작성자와 같다면 알림은 스킵
        if (Objects.equals(comment.getPostOwnerId(), comment.getWriterId())) {
            return; // 글 작성자와 댓글 작성자가 같으면 알림 X
        }

        CommentNotification notification = createNotification(comment, now);

        // DB에 저장
        savePort.saveNotification(notification);

    }

    @Override
    public void removeNotification(Comment comment) {
        deletePort.deleteCommentNotification(comment.getCommentId());
    }

    private CommentNotification createNotification(Comment comment, LocalDateTime now) {
        // 알림 생성
        return CommentNotification.of
                (NotificationIdGenerator.generate(),
                        comment.getPostOwnerId(),
                        NotificationType.COMMENT,
                        comment.getOccurredAt(),
                        now,
                        now,
                        now.plus(90, ChronoUnit.DAYS),
                        comment.getPostId(),
                        comment.getWriterId(),
                        comment.getCommentId(),
                        comment.getComment());
    }


}

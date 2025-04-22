package com.zipte.platform.server.application.out.notification.comment;

import com.zipte.platform.server.domain.notification.CommentNotification;

public interface SaveCommentNotificationPort {

    CommentNotification saveNotification(CommentNotification notification);


}

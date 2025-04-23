package com.zipte.platform.server.application.in.task;


import com.zipte.platform.server.domain.community.comment.Comment;

public interface CommentNotificationTask {

    void createNotification(Comment commentEvent);

    void removeNotification(Comment commentEvent);

}

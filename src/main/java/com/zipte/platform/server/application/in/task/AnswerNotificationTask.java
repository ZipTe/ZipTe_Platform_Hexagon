package com.zipte.platform.server.application.in.task;


import com.zipte.platform.server.domain.community.Answer;

public interface AnswerNotificationTask {

    void createNotification(Answer answer);

    void removeNotification(Long answerId);


}

package com.zipte.platform.server.application.out.notification.answer;

import com.zipte.platform.server.domain.notification.AnswerNotification;

public interface SaveAnswerNotificationPort {

    AnswerNotification saveNotification(AnswerNotification notification);


}

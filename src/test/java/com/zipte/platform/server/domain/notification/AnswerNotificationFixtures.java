package com.zipte.platform.server.domain.notification;

import com.zipte.platform.server.adapter.out.mongo.notification.base.NotificationType;

import java.time.LocalDateTime;
import java.util.UUID;

public class AnswerNotificationFixtures {

    public static AnswerNotification stub(String id) {

        return AnswerNotification.of(
                id,          // id
                1L,                                     // userId
                NotificationType.ANSWER,               // type (ENUM 값에 맞게 조정)
                LocalDateTime.now().minusHours(1),     // occurredAt
                LocalDateTime.now(),                   // createdAt
                LocalDateTime.now(),                   // lastUpdatedAt
                LocalDateTime.now(),                                   // deleteAt
                2L,                                   // questionId
                3L,                                   // writerId
                4L,                                   // answerId
                "이것은 테스트 알림입니다."             // content
        );
    }


    public static AnswerNotification stub(Long userId, Long questionId, Long answerId, Long writerId) {

        return AnswerNotification.of(
                UUID.randomUUID().toString(),          // id
                userId,                                     // userId
                NotificationType.ANSWER,               // type (ENUM 값에 맞게 조정)
                LocalDateTime.now().minusHours(1),     // occurredAt
                LocalDateTime.now(),                   // createdAt
                LocalDateTime.now(),                   // lastUpdatedAt
                LocalDateTime.now(),                                   // deleteAt
                questionId,                                   // questionId
                writerId,                                   // writerId
                answerId,                                   // answerId
                "이것은 테스트 알림입니다."             // content
        );
    }

}

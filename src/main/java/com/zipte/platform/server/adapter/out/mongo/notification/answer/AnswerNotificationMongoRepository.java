package com.zipte.platform.server.adapter.out.mongo.notification.answer;

import com.zipte.platform.server.adapter.out.mongo.notification.base.NotificationMongoRepository;

import java.util.Optional;

public interface AnswerNotificationMongoRepository extends NotificationMongoRepository<AnswerNotificationDocument, String> {


    Optional<AnswerNotificationDocument> findByAnswerId(Long answerId);


}

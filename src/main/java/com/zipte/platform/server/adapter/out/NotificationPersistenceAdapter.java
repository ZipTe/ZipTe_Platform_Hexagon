package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.mongo.notification.base.NotificationDocument;
import com.zipte.platform.server.adapter.out.mongo.notification.base.NotificationMongoRepository;
import com.zipte.platform.server.adapter.out.mongo.notification.answer.AnswerNotificationDocument;
import com.zipte.platform.server.adapter.out.mongo.notification.answer.AnswerNotificationMongoRepository;
import com.zipte.platform.server.adapter.out.mongo.notification.property.PropertyDocument;
import com.zipte.platform.server.adapter.out.mongo.notification.property.PropertyMongoRepository;
import com.zipte.platform.server.application.out.notification.LoadNotificationPort;
import com.zipte.platform.server.domain.notification.AnswerNotification;
import com.zipte.platform.server.domain.notification.Notification;
import com.zipte.platform.server.domain.notification.PropertyNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotificationPersistenceAdapter implements LoadNotificationPort {

    /*
        알림에 대한 정보를 가져온다.
     */

    private final NotificationMongoRepository<NotificationDocument, String> repository;
    private final AnswerNotificationMongoRepository answerRepository;
    private final PropertyMongoRepository propertyRepository;

    @Override
    public Page<Notification> loadNotifications(Long userId, Pageable pageable) {
        return repository.findAllByUserIdOrderByOccurredAtDesc(userId, pageable)
                .map(NotificationDocument::toDomain);
    }

    @Override
    public Optional<Notification> loadNotificationsAt(Long userId) {
        return propertyRepository.findFirstByUserIdOrderByOccurredAtDesc(userId)
                .map(NotificationDocument::toDomain);

    }

    @Override
    public Optional<AnswerNotification> loadAnswerNotification(Long answerId) {

        return answerRepository.findByAnswerId(answerId)
                .map(AnswerNotificationDocument::toDomain);
    }

    @Override
    public Optional<PropertyNotification> loadPropertyNotification(String complexCode) {
        return propertyRepository.findByKaptCode(complexCode)
                .map(PropertyDocument::toDomain);
    }


}

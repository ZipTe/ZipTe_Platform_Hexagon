package com.zipte.platform.server.application.service.task;

import com.zipte.platform.core.util.NotificationIdGenerator;
import com.zipte.platform.server.adapter.out.mongo.notification.base.NotificationType;
import com.zipte.platform.server.application.in.task.PropertyNotificationTask;
import com.zipte.platform.server.application.out.favorite.FavoritePort;
import com.zipte.platform.server.application.out.notification.property.DeletePropertyNotificationPort;
import com.zipte.platform.server.application.out.notification.property.SavePropertyNotificationPort;
import com.zipte.platform.server.domain.notification.PropertyNotification;
import com.zipte.platform.server.domain.property.Property;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;


@Slf4j
@Component
@RequiredArgsConstructor
public class PropertyNotificationService implements PropertyNotificationTask {

    private final SavePropertyNotificationPort savePort;
    private final DeletePropertyNotificationPort deletePort;

    // 의존성
    private final FavoritePort favoritePort;

    @Override
    public void createNotification(Property property) {

        LocalDateTime now = LocalDateTime.now();

        // 모든 알림이 동일하게 생성
        List<Long> favoriteUsers = favoritePort.loadUserFavoriteByComplexCode(property.getKaptCode());

        // 관심 목록 설정한 유저에게 알림 제공
        favoriteUsers.forEach(userId -> {

            // 매물 작성자와 같다면 알림은 스킵
            if (Objects.equals(property.getOwnerId(), userId)) {
                return;
            }

            PropertyNotification notification = createNotification(property, now, userId);
            savePort.saveNotification(notification);
        });
    }

    @Override
    public void removeNotification(Property property) {

        // 매물 삭제로 인한 알림 삭제
        deletePort.deleteCommentNotification(property.getKaptCode());
    }

    private PropertyNotification createNotification(Property property, LocalDateTime now, Long userId) {

        return PropertyNotification.of(
                NotificationIdGenerator.generate(),
                NotificationType.PROPERTY,
                userId,
                property.getCreatedAt(),
                now,
                now,
                now.plus(90, ChronoUnit.DAYS),
                property.getKaptCode(),
                property.getPrice());
    }

}

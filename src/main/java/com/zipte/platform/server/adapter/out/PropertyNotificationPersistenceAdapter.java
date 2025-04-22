package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.mongo.notification.property.PropertyDocument;
import com.zipte.platform.server.adapter.out.mongo.notification.property.PropertyMongoRepository;
import com.zipte.platform.server.application.out.notification.property.DeletePropertyNotificationPort;
import com.zipte.platform.server.application.out.notification.property.SavePropertyNotificationPort;
import com.zipte.platform.server.domain.notification.PropertyNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PropertyNotificationPersistenceAdapter implements SavePropertyNotificationPort, DeletePropertyNotificationPort {

    private final PropertyMongoRepository repository;

    /*
        알림에 대한 저장 및 삭제를 수행한다.
     */

    @Override
    public PropertyNotification saveNotification(PropertyNotification notification) {
        var document = PropertyDocument.from(notification);

        return repository.save(document)
                .toDomain();
    }

    @Override
    public void deleteCommentNotification(String complexCode) {
        repository.deleteById(complexCode);
    }
}

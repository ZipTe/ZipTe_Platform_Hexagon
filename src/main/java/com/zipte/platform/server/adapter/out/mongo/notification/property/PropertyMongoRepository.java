package com.zipte.platform.server.adapter.out.mongo.notification.property;

import com.zipte.platform.server.adapter.out.mongo.notification.base.NotificationDocument;
import com.zipte.platform.server.adapter.out.mongo.notification.base.NotificationMongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface PropertyMongoRepository extends NotificationMongoRepository<PropertyDocument, String> {


    void deleteByComplexCode(String complexCode);

    Optional<NotificationDocument> findFirstByUserIdOrderByOccurredAtDesc(Long userId);

    @Query("{'complexCode' : ?0}")
    Optional<PropertyDocument> findByComplexCode(String complexCode);


}

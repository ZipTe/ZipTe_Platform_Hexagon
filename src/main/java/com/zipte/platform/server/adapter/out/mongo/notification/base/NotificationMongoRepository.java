package com.zipte.platform.server.adapter.out.mongo.notification.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationMongoRepository<T extends NotificationDocument, ID>  extends MongoRepository<T, ID> {

    // 알림 목록 조회하기 (처음부터)
    @Query("{'userId': ?0 }")
    Page<NotificationDocument> findAllByUserIdOrderByOccurredAtDesc(Long userId, Pageable pageable);

    // 제일 최신 알림, 읽음 시간 가져오기
    Optional<NotificationDocument> findFirstByUserIdOrderByOccurredAtDesc(Long userId);


}

package com.zipte.platform.server.adapter.out.mongo.notification.comment;

import com.zipte.platform.server.adapter.out.mongo.notification.base.NotificationMongoRepository;

import java.util.Optional;

public interface CommentMongoRepository extends NotificationMongoRepository<CommentDocument, String> {


    Optional<CommentDocument> findByCommentId(Long commentId);

}

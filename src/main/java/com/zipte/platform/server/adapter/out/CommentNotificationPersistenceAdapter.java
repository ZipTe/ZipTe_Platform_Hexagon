package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.mongo.notification.comment.CommentDocument;
import com.zipte.platform.server.adapter.out.mongo.notification.comment.CommentMongoRepository;
import com.zipte.platform.server.application.out.notification.comment.DeleteCommentNotificationPort;
import com.zipte.platform.server.application.out.notification.comment.SaveCommentNotificationPort;
import com.zipte.platform.server.domain.notification.CommentNotification;import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CommentNotificationPersistenceAdapter implements SaveCommentNotificationPort, DeleteCommentNotificationPort {

    private final CommentMongoRepository repository;

    /*
        알림에 대한 저장 및 삭제를 수행한다.
     */

    @Override
    public CommentNotification saveNotification(CommentNotification comment) {
        var commentDocument = CommentDocument.from(comment);

        return repository.save(commentDocument)
                .toDomain();
    }


    @Override
    public void deleteCommentNotification(Long commentId) {
        CommentDocument document = repository.findByCommentId(commentId)
                .orElseThrow(() -> new EntityNotFoundException("값이 존재하지않습니다"));

        repository.deleteById(document.getId());
    }
}

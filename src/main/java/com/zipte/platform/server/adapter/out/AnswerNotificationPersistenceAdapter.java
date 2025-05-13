package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.mongo.notification.answer.AnswerNotificationDocument;
import com.zipte.platform.server.adapter.out.mongo.notification.answer.AnswerNotificationMongoRepository;
import com.zipte.platform.server.application.out.notification.answer.DeleteAnswerNotificationPort;
import com.zipte.platform.server.application.out.notification.answer.SaveAnswerNotificationPort;
import com.zipte.platform.server.domain.notification.AnswerNotification;import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AnswerNotificationPersistenceAdapter implements SaveAnswerNotificationPort, DeleteAnswerNotificationPort {

    private final AnswerNotificationMongoRepository repository;

    /*
        알림에 대한 저장 및 삭제를 수행한다.
     */

    @Override
    public AnswerNotification saveNotification(AnswerNotification answer) {
        var answerDocument = AnswerNotificationDocument.from(answer);

        return repository.save(answerDocument)
                .toDomain();
    }


    @Override
    public void deleteAnswerNotification(Long answerId) {
        AnswerNotificationDocument document = repository.findByAnswerId(answerId)
                .orElseThrow(() -> new EntityNotFoundException("값이 존재하지않습니다"));

        repository.deleteById(document.getId());
    }
}

package com.zipte.platform.server.application.service.task;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.core.util.NotificationIdGenerator;
import com.zipte.platform.server.adapter.out.mongo.notification.base.NotificationType;
import com.zipte.platform.server.application.in.task.AnswerNotificationTask;
import com.zipte.platform.server.application.out.community.QuestionPort;
import com.zipte.platform.server.application.out.notification.answer.DeleteAnswerNotificationPort;
import com.zipte.platform.server.application.out.notification.answer.SaveAnswerNotificationPort;
import com.zipte.platform.server.domain.community.Answer;
import com.zipte.platform.server.domain.community.Question;
import com.zipte.platform.server.domain.notification.AnswerNotification;import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerNotificationService implements AnswerNotificationTask {

    private final SaveAnswerNotificationPort savePort;
    private final DeleteAnswerNotificationPort deletePort;

    /// 의존성
    private QuestionPort questionPort;

    @Override
    public void createNotification(Answer answer) {

        LocalDateTime now = LocalDateTime.now();

        /// 답변한 질문 가져오기
        Question question = questionPort.loadQuestion(answer.getQuestionId())
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_QUESTION.getMessage()));

        // 질문 작성자와 같다면 알림은 스킵
        if (Objects.equals(question.getUserId(), answer.getUserId())) {
            return; // 질문 작성자와 답변 작성자가 같으면 알림 X
        }

        AnswerNotification notification = createNotification(answer, question.getUserId(), now);

        // DB에 저장
        savePort.saveNotification(notification);

    }

    @Override
    public void removeNotification(Answer answer) {
        deletePort.deleteAnswerNotification(answer.getId());
    }

    private AnswerNotification createNotification(Answer answer, Long questionUserId ,LocalDateTime now) {

        // 알림 생성
        return AnswerNotification.of
                (NotificationIdGenerator.generate(),
                        questionUserId,
                        NotificationType.ANSWER,
                        answer.getCreatedAt(),
                        now,
                        now,
                        now.plus(90, ChronoUnit.DAYS),
                        answer.getQuestionId(),
                        answer.getUserId(),
                        answer.getId(),
                        answer.getContent());
    }


}

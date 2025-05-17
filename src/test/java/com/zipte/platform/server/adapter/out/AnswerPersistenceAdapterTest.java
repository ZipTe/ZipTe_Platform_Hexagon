package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.jpa.community.AnswerJpaEntity;
import com.zipte.platform.server.adapter.out.jpa.community.AnswerJpaRepository;
import com.zipte.platform.server.domain.community.Answer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AnswerPersistenceAdapterTest {

    @Autowired
    private AnswerJpaRepository repository;

    @Autowired
    private AnswerPersistenceAdapter sut;


    @Nested
    @DisplayName("저장 테스트")
    class Save {

        @Test
        @DisplayName("[happy] 답변 저장 및 조회 테스트")
        void saveAndFindById() {
            // Given

            Long userId = 1L;
            Long questionId = 2L;
            String content = "테스트";

            Answer domain = Answer.of(userId, questionId, content);

            // When
            Answer saved = sut.saveAnswer(domain);

            // Then
            Optional<AnswerJpaEntity> result = repository.findById(saved.getId());
            assertThat(result)
                    .isPresent();
            assertThat(result.get()
                    .getContent())
                    .isEqualTo(content);
        }

    }

    @Nested
    @DisplayName("조회 테스트")
    class Get {

        @Test
        @DisplayName("[happy] ID로 조회 테스트")
        void loadById() {
            // Given
            Answer answer = sut.saveAnswer(Answer.of(1L, 2L, "조회 테스트"));

            // When
            Optional<Answer> result = sut.loadAnswerById(answer.getId());

            // Then
            assertThat(result).isPresent();
            assertThat(result.get()
                    .getContent())
                    .isEqualTo("조회 테스트");
        }

        @Test
        @DisplayName("[happy] 질문 ID 기준으로 첫 번째 답변 조회")
        void loadByQuestionIdFirst() {
            // Given
            Long questionId = 100L;
            sut.saveAnswer(Answer.of(1L, questionId, "첫 번째"));
            sut.saveAnswer(Answer.of(2L, questionId, "두 번째"));

            // When
            Optional<Answer> result = sut.loadAnswerByQuestionIdFirst(questionId);

            // Then
            assertThat(result).isPresent();
            assertThat(result.get()
                    .getContent())
                    .isEqualTo("첫 번째");
        }

        @Test
        @DisplayName("[happy] 질문 ID로 전체 답변 리스트 조회")
        void loadAllByQuestionId() {
            // Given
            Long questionId = 200L;
            sut.saveAnswer(Answer.of(1L, questionId, "답변 A"));
            sut.saveAnswer(Answer.of(2L, questionId, "답변 B"));

            // When
            List<Answer> result = sut.loadAnswerByQuestionId(questionId);

            // Then
            assertThat(result).hasSize(2);
            assertThat(result).extracting(Answer::getContent).containsExactlyInAnyOrder("답변 A", "답변 B");
        }

    }

    @Nested
    @DisplayName("삭제 테스트")
    class Delete {

        @Test
        @DisplayName("[happy] 답변 삭제 테스트")
        void deleteAnswer() {
            // Given
            Answer answer = sut.saveAnswer(Answer.of(1L, 2L, "삭제될 답변"));
            Long id = answer.getId();

            // When
            sut.deleteAnswerById(id);

            // Then
            Optional<AnswerJpaEntity> result = repository.findById(id);
            assertThat(result).isNotPresent();
        }

    }

    @Nested
    @DisplayName("존재 여부 확인 테스트")
    class Exists {

        @Test
        @DisplayName("[happy] ID와 User ID로 존재 여부 확인")
        void existsByIdAndUserId() {
            // Given
            Answer answer = sut.saveAnswer(Answer.of(10L, 20L, "존재 여부"));

            // When
            boolean exists = sut.checkExistAnswerByIdAndUserId(answer.getId(), 10L);

            // Then
            assertThat(exists).isTrue();
        }

        @Test
        @DisplayName("[unhappy] 존재하지 않을 경우 false")
        void notExists() {
            // When
            boolean exists = sut.checkExistAnswerByIdAndUserId(999L, 999L);

            // Then
            assertThat(exists).isFalse();
        }
    }

}

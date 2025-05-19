package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.jpa.community.AnswerJpaEntity;
import com.zipte.platform.server.adapter.out.jpa.community.AnswerJpaRepository;
import com.zipte.platform.server.domain.community.Answer;
import com.zipte.platform.server.domain.qa.AnswerJpaFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnswerPersistenceAdapterTest {

    @Mock
    private AnswerJpaRepository repository;

    @InjectMocks
    private AnswerPersistenceAdapter sut;

    @Test
    @DisplayName("[happy] 답변 저장 및 조회 테스트")
    void save_happy() {

        // Given
        Long userId = 1L;
        Long questionId = 2L;
        String content = "테스트";

        Answer domain = Answer.of(userId, questionId, content);
        AnswerJpaEntity answer = AnswerJpaFixtures.stubAnswer(100L, userId, questionId, content);

        given(repository.save(any(AnswerJpaEntity.class)))
                .willReturn(answer);

        // When
        sut.saveAnswer(domain);

        // Then
        verify(repository, times(1)).save(any(AnswerJpaEntity.class));
    }

    @Nested
    @DisplayName("조회 테스트")
    class Get {

        @Test
        @DisplayName("[happy] ID로 조회 테스트")
        void loadById() {
            // Given
            Long id = 1L;
            AnswerJpaEntity entity = AnswerJpaFixtures.stubAnswer(id, 1L, 2L, "조회 테스트");

            given(repository.findById(id))
                    .willReturn(Optional.of(entity));

            // When
            Optional<Answer> answer = sut.loadAnswerById(id);
            Answer answer1 = answer.get();

            // Then
            then(answer1)
                    .isNotNull()
                    .hasFieldOrPropertyWithValue("id", id);

            verify(repository, times(1)).findById(id);
        }

        @Test
        @DisplayName("[happy] 질문 ID 기준으로 첫 번째 답변 조회")
        void loadByQuestionIdFirst() {
            // Given
            Long questionId = 100L;
            AnswerJpaEntity entity = AnswerJpaFixtures.stubAnswer(1L, 1L, questionId, "첫 번째");

            given(repository.findTop1ByQuestionIdOrderByCreatedAtAsc(questionId))
                    .willReturn(Optional.of(entity));

            // When
            Optional<Answer> result = sut.loadAnswerByQuestionIdFirst(questionId);

            // Then
            assertThat(result).isPresent();
            assertThat(result.get()
                    .getContent())
                    .isEqualTo("첫 번째");

            verify(repository, times(1)).findTop1ByQuestionIdOrderByCreatedAtAsc(questionId);

        }

        @Test
        @DisplayName("[happy] 질문 ID로 전체 답변 리스트 조회")
        void loadAllByQuestionId() {
            // Given
            Long questionId = 200L;
            AnswerJpaEntity entityA = AnswerJpaFixtures.stubAnswer(1L, 1L, questionId, "답변 A");
            AnswerJpaEntity entityB = AnswerJpaFixtures.stubAnswer(2L, 2L, questionId, "답변 B");

            given(repository.findByQuestionId(questionId))
                    .willReturn(List.of(entityA, entityB));

            // When
            List<Answer> result = sut.loadAnswerByQuestionId(questionId);

            // Then
            assertThat(result).hasSize(2);
            assertThat(result).extracting(Answer::getContent).containsExactlyInAnyOrder("답변 A", "답변 B");
            verify(repository, times(1)).findByQuestionId(questionId);
        }
    }

    @Nested
    @DisplayName("삭제 테스트")
    class Delete {

        @Test
        @DisplayName("[happy] 답변 삭제 테스트")
        void deleteAnswer() {
            // Given
            Long id = 1L;

            doNothing().when(repository).deleteById(id);

            // When
            sut.deleteAnswerById(id);

            // Then
            verify(repository, times(1)).deleteById(id);
        }
    }

    @Nested
    @DisplayName("존재 여부 확인 테스트")
    class Exists {

        @Test
        @DisplayName("[happy] ID와 User ID로 존재 여부 확인")
        void existsByIdAndUserId() {
            // Given
            Long id = 1L;
            Long userId = 10L;

            given(repository.existsByIdAndUserId(id, userId))
                    .willReturn(true);

            // When
            boolean exists = sut.checkExistAnswerByIdAndUserId(id, userId);

            // Then
            assertThat(exists).isTrue();
            verify(repository, times(1)).existsByIdAndUserId(id, userId);
        }

        @Test
        @DisplayName("[unhappy] 존재하지 않을 경우 false")
        void notExists() {
            // Given
            given(repository.existsByIdAndUserId(anyLong(), anyLong()))
                    .willReturn(false);

            // When
            boolean exists = sut.checkExistAnswerByIdAndUserId(999L, 999L);

            // Then
            assertThat(exists).isFalse();
            verify(repository, times(1)).existsByIdAndUserId(999L, 999L);
        }
    }
}

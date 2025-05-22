package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.jpa.community.QuestionJpaEntity;
import com.zipte.platform.server.adapter.out.jpa.community.QuestionJpaRepository;
import com.zipte.platform.server.domain.community.Question;
import com.zipte.platform.server.domain.qa.QuestionJpaFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuestionPersistenceAdapterTest {

    @Mock
    private QuestionJpaRepository repository;

    @InjectMocks
    private QuestionPersistenceAdapter sut;

    @Nested
    @DisplayName("저장")
    class SaveQuestion {

        @Test
        @DisplayName("[happy] 질문 저장 성공")
        void save() {
            // Given
            Long userId = 1L;
            String title = "질문 제목";
            String content = "질문 내용";
            String kaptCode = "12345678";

            Question domain = Question.of(userId, kaptCode, title, content);

            QuestionJpaEntity savedEntity = QuestionJpaFixtures.stub(1L, userId, kaptCode, title, content); // 저장 후 반환된 엔티티 예시

            given(repository.save(any(QuestionJpaEntity.class)))
                    .willReturn(savedEntity);

            // When
            Question saved = sut.save(domain);

            // Then
            verify(repository)
                    .save(any(QuestionJpaEntity.class));

            assertThat(saved.getId()).isEqualTo(savedEntity.getId());
            assertThat(saved.getTitle()).isEqualTo(title);
            assertThat(saved.getContent()).isEqualTo(content);
        }
    }

    @Nested
    @DisplayName("조회")
    class GetQuestion {

        @Test
        @DisplayName("[happy] 질문 ID로 조회 성공")
        void get() {
            // Given
            Long questionId = 1L;
            QuestionJpaEntity entity = QuestionJpaFixtures.stub(questionId);

            given(repository.findById(questionId))
                    .willReturn(Optional.of(entity));

            // When
            Optional<Question> result = sut.loadQuestion(questionId);

            // Then
            verify(repository)
                    .findById(questionId);

            assertThat(result)
                    .isPresent();
            assertThat(result.get().getTitle())
                    .isEqualTo("기본 제목");
        }
    }

    @Nested
    @DisplayName("삭제")
    class DeleteQuestion {

        @Test
        @DisplayName("[happy] 질문 삭제 성공")
        void delete() {
            // Given
            Long questionId = 1L;
            willDoNothing().given(repository).deleteById(questionId);

            // When
            sut.deleteQuestionById(questionId);

            // Then
            verify(repository).deleteById(questionId);
        }
    }

    @Nested
    @DisplayName("존재 여부 확인 테스트")
    class Exists {

        @Test
        @DisplayName("[happy] 질문 존재 여부 확인")
        void checkExistQuestion() {
            // Given
            Long questionId = 1L;
            given(repository.existsById(questionId)).willReturn(true);

            // When
            boolean exists = sut.checkExistQuestion(questionId);

            // Then
            then(repository).should().existsById(questionId);
            assertThat(exists).isTrue();
        }

        @Test
        @DisplayName("[happy] 질문 ID와 유저 ID로 존재 여부 확인")
        void checkExistQuestionByIdAndUserId() {
            // Given
            Long questionId = 1L;
            Long userId = 1L;
            given(repository.existsByIdAndUserId(questionId, userId)).willReturn(true);

            // When
            boolean exists = sut.checkExistQuestionByIdAndUserId(questionId, userId);

            // Then
            verify(repository)
                    .existsByIdAndUserId(questionId, userId);

            assertThat(exists).isTrue();
        }
    }
}


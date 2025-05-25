package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.jpa.community.QuestionJpaEntity;
import com.zipte.platform.server.adapter.out.jpa.community.QuestionJpaRepository;
import com.zipte.platform.server.domain.community.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class QuestionPersistenceAdapterTest {

    @Autowired
    private QuestionJpaRepository repository;

    @Autowired
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

            // When
            Question saved = sut.save(domain);

            // Then
            Optional<QuestionJpaEntity> result = repository.findById(saved.getId());

            assertThat(result).isPresent();
            assertThat(result.get().getContent()).isEqualTo(content);
            assertThat(result.get().getTitle()).isEqualTo(title);
        }
    }

    @Nested
    @DisplayName("조회")
    class GetQuestion {

        @Test
        @DisplayName("[happy] 질문 ID로 조회 성공")
        void get() {
            // Given
            Question saved = sut.save(Question.of(1L, "A10000001", "제목", "내용"));

            // When
            Optional<Question> result = sut.loadQuestion(saved.getId());

            // Then
            assertThat(result).isPresent();
            assertThat(result.get().getTitle()).isEqualTo("제목");
        }
    }

    @Nested
    @DisplayName("삭제")
    class DeleteQuestion {

        @Test
        @DisplayName("[happy] 질문 삭제 성공")
        void delete() {
            // Given
            Question saved = sut.save(Question.of(1L, "삭제용", "내용", "A10000001"));

            // When
            sut.deleteQuestionById(saved.getId());

            // Then
            Optional<QuestionJpaEntity> result = repository.findById(saved.getId());
            assertThat(result).isNotPresent();
        }
    }

    @Nested
    @DisplayName("존재 여부 확인 테스트")
    class Exists {

        @Test
        @DisplayName("[happy] 질문 존재 여부 확인")
        void checkExistQuestion() {
            // Given
            Question saved = sut.save(Question.of(1L, "제목", "내용", "A10000001"));

            // When
            boolean exists = sut.checkExistQuestion(saved.getId());

            // Then
            assertThat(exists).isTrue();
        }

        @Test
        @DisplayName("[happy] 질문 ID와 유저 ID로 존재 여부 확인")
        void checkExistQuestionByIdAndUserId() {
            // Given
            Long userId = 1L;
            Question saved = sut.save(Question.of(userId, "제목", "내용", "A10000001"));

            // When
            boolean exists = sut.checkExistQuestionByIdAndUserId(saved.getId(), userId);

            // Then
            assertThat(exists).isTrue();
        }
    }

}

package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.external.elk.community.QuestionELKDocument;
import com.zipte.platform.server.adapter.out.external.elk.community.QuestionELKRepository;
import com.zipte.platform.server.adapter.out.jpa.community.QuestionJpaEntity;
import com.zipte.platform.server.adapter.out.jpa.community.QuestionJpaRepository;
import com.zipte.platform.server.application.out.community.QuestionPort;
import com.zipte.platform.server.domain.community.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuestionPersistenceAdapter implements QuestionPort {

    private final QuestionJpaRepository repository;

    /// 엘라스틱 저장 의존성 추가
    private final QuestionELKRepository elkRepository;

    /// 저장
    @Override
    public Question save(Question question) {

        /// JPA 관련
        var entity = QuestionJpaEntity.from(question);
        Question domain = repository.save(entity)
                .toDomain();

        // ELK 관련
        QuestionELKDocument elkDocument = QuestionELKDocument.from(domain);
        elkRepository.save(elkDocument);

        return domain;
    }

    /// 조회
    @Override
    public Optional<Question> loadQuestion(Long id) {
        return repository.findById(id)
                .map(QuestionJpaEntity::toDomain);
    }

    @Override
    public Page<Question> loadQuestionsByKaptCode(String kaptCode, Pageable pageable) {
        return repository.findByKaptCode(kaptCode, pageable)
                .map(QuestionJpaEntity::toDomain);
    }

    @Override
    public Page<Question> loadQuestionsByKeyword(String kaptCode, String keyword, Pageable pageable) {
        return elkRepository.findByTitleAndKaptCode(keyword, kaptCode, pageable)
                .map(QuestionELKDocument::toDomain);
    }

    @Override
    public List<Question> loadQuestionsByKeyword(String kaptCode, String keyword) {
        return elkRepository.findByTitle(keyword).stream()
                .map(QuestionELKDocument::toDomain)
                .toList();
    }


    /// 삭제
    @Override
    public void deleteQuestionById(Long id) {
        repository.deleteById(id);
    }


    /// 체크
    @Override
    public boolean checkExistQuestion(Long questionId) {
        return repository.existsById(questionId);
    }

    @Override
    public boolean checkExistQuestionByIdAndUserId(Long id, Long userId) {
        return repository.existsByIdAndUserId(id, userId);
    }

}

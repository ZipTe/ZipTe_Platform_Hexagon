package com.zipte.platform.server.application.service;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.server.adapter.in.web.dto.request.QuestionAnswerDetailResponse;
import com.zipte.platform.server.adapter.in.web.dto.request.QuestionRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.QuestionAnswerListResponse;
import com.zipte.platform.server.application.in.community.QuestionUseCase;
import com.zipte.platform.server.application.out.community.AnswerPort;
import com.zipte.platform.server.application.out.community.QuestionPort;
import com.zipte.platform.server.application.out.estate.LoadEstatePort;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.community.Answer;
import com.zipte.platform.server.domain.community.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService implements QuestionUseCase {

    private final QuestionPort questionPort;

    /// 외부 의존성
    private final UserPort userPort;
    private final LoadEstatePort loadEstatePort;

    private final AnswerPort answerPort;

    /**
     * Creates and saves a new question after validating the existence of the user and estate.
     *
     * @param request the question creation request containing user ID, estate code, title, and content
     * @return the saved Question entity
     * @throws NoSuchElementException if the user or estate does not exist
     */
    @Override
    public Question createQuestion(QuestionRequest request) {

        /// 유저 예외 처리
        boolean userChecked = userPort.checkExistingById(request.getUserId());
        if (!userChecked) {
            throw new NoSuchElementException(ErrorCode.NOT_USER.getMessage());
        }

        /// 아파트 예외처리
        boolean estateChecked = loadEstatePort.checkExistingByCode(request.getKaptCode());
        if (!estateChecked) {
            throw new NoSuchElementException(ErrorCode.NOT_ESTATE.getMessage());
        }

        /// 객체 생성
        Question question = Question.of(request.getUserId(), request.getKaptCode(), request.getTitle(), request.getContent());

        return questionPort.save(question);
    }

    /**
     * Retrieves detailed information for a specific question, including all associated answers.
     *
     * @param questionId the unique identifier of the question to retrieve
     * @return a response DTO containing the question and its answers
     * @throws NoSuchElementException if the question does not exist
     */
    @Override
    public QuestionAnswerDetailResponse loadQuestion(Long questionId) {

        /// 질문
        // 예외처리하기
        Question question = questionPort.loadQuestion(questionId)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_QUESTION.getMessage()));

        /// 답변
        List<Answer> answers = answerPort.loadAnswerByQuestionId(questionId);

        /// DTO 변환
        return QuestionAnswerDetailResponse.from(question, answers);
    }

    /**
     * Retrieves a paginated list of questions for a given estate code, each paired with its first associated answer if available.
     *
     * @param kaptCode the estate code to filter questions by
     * @param pageable pagination information
     * @return a page of question and answer summary response DTOs
     * @throws NoSuchElementException if the estate with the given code does not exist
     */
    @Override
    public Page<QuestionAnswerListResponse> loadQuestions(String kaptCode, Pageable pageable) {

        /// 예외처리
        boolean checked = loadEstatePort.checkExistingByCode(kaptCode);
        if (!checked) {
            throw new NoSuchElementException(ErrorCode.NOT_ESTATE.getMessage());
        }

        /// 아파트 조회하기
        Page<Question> questions = questionPort.loadQuestionsByKaptCode(kaptCode, pageable);

        List<Question> content = questions.getContent();

        /// DTO
        List<QuestionAnswerListResponse> responseList = new ArrayList<>();

        /// 답변 조회하기
        content.forEach(question -> {
            Optional<Answer> answer = answerPort.loadAnswerByQuestionIdFirst(question.getId());
            QuestionAnswerListResponse response = QuestionAnswerListResponse.from(question, answer);
            responseList.add(response);
        });

        return new PageImpl<>(responseList, pageable, questions.getTotalElements());
    }

}

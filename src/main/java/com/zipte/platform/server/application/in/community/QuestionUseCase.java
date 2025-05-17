package com.zipte.platform.server.application.in.community;

import com.zipte.platform.server.adapter.in.web.dto.request.QuestionAnswerDetailResponse;
import com.zipte.platform.server.adapter.in.web.dto.request.QuestionRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.QuestionAnswerListResponse;
import com.zipte.platform.server.domain.community.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionUseCase {

    /*
        - 로그인한 유저는 단지별로 질문을 남기고, 답변을 받을 수 있다
     */


    /**
 * Creates a new question related to an apartment complex.
 *
 * @param request the details of the question to be created
 * @return the created Question entity
 */
    Question createQuestion(QuestionRequest request);

    /**
 * Retrieves detailed information about a question and its answers by question ID.
 *
 * @param questionId the unique identifier of the question to retrieve
 * @return a detailed response containing the question and its associated answers
 */
    QuestionAnswerDetailResponse loadQuestion(Long questionId);

    /**
 * Retrieves a paginated list of questions for a specified apartment complex.
 *
 * @param kaptCode the unique code identifying the apartment complex
 * @param pageable pagination information for the result set
 * @return a page of questions and their associated answers
 */
    Page<QuestionAnswerListResponse> loadQuestions(String kaptCode, Pageable pageable);

}

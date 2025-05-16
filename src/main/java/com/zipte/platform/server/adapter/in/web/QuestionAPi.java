package com.zipte.platform.server.adapter.in.web;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.core.response.pageable.PageRequest;
import com.zipte.platform.core.response.pageable.PageResponse;
import com.zipte.platform.server.adapter.in.web.dto.request.AnswerRequest;
import com.zipte.platform.server.adapter.in.web.dto.request.QuestionAnswerDetailResponse;
import com.zipte.platform.server.adapter.in.web.dto.request.QuestionRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.QuestionAnswerListResponse;
import com.zipte.platform.server.application.in.community.AnswerUseCase;
import com.zipte.platform.server.application.in.community.QuestionUseCase;
import com.zipte.platform.server.domain.community.Answer;
import com.zipte.platform.server.domain.community.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class QuestionAPi {

    /// 질문
    private final QuestionUseCase questionService;

    /// 답변
    private final AnswerUseCase answerService;

    @PostMapping("/question")
    public ApiResponse<String> createQuestion(@RequestBody QuestionRequest request) {

        Question question = questionService.createQuestion(request);
        log.info("Question: {}", question.toString());

        return ApiResponse.ok("성공적으로 질문이 저장되었습니다.");

    }

    @GetMapping("/question/{kaptCode}")
    public ApiResponse<PageResponse<QuestionAnswerListResponse>> getQuestions(
            @PathVariable String kaptCode,
            PageRequest pageRequest) {

        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        /// 페이징
        Page<QuestionAnswerListResponse> result = questionService.loadQuestions(kaptCode, pageable);
        List<QuestionAnswerListResponse> dtolist = result.getContent();


        return ApiResponse.ok(new PageResponse<>(dtolist, pageRequest, result.getTotalElements()));
    }

    @GetMapping("/question/detail/{questionId}")
    public ApiResponse<QuestionAnswerDetailResponse> getQuestion(
            @PathVariable Long questionId) {

        QuestionAnswerDetailResponse response = questionService.loadQuestion(questionId);

        return ApiResponse.ok(response);
    }



    @PostMapping("/answer")
    public ApiResponse<String> createAnswer(@RequestBody AnswerRequest request) {

        Answer answer = answerService.createComment(request);
        log.info("Answer: {}", answer.toString());

        return ApiResponse.ok("성공적으로 답변이 저장되었습니다.");

    }
}

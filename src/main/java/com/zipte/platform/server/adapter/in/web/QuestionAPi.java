package com.zipte.platform.server.adapter.in.web;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.server.adapter.in.web.dto.request.AnswerRequest;
import com.zipte.platform.server.adapter.in.web.dto.request.QuestionAnswerResponse;
import com.zipte.platform.server.adapter.in.web.dto.request.QuestionRequest;
import com.zipte.platform.server.application.in.community.AnswerUseCase;
import com.zipte.platform.server.application.in.community.QuestionUseCase;
import com.zipte.platform.server.domain.community.Answer;
import com.zipte.platform.server.domain.community.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/question/{questionId}")
    public ApiResponse<QuestionAnswerResponse> getQuestion(@PathVariable Long questionId) {

        QuestionAnswerResponse response = questionService.loadQuestion(questionId);

        return ApiResponse.ok(response);
    }

    @PostMapping("/answer")
    public ApiResponse<String> createAnswer(@RequestBody AnswerRequest request) {

        Answer answer = answerService.createComment(request);
        log.info("Answer: {}", answer.toString());

        return ApiResponse.ok("성공적으로 답변이 저장되었습니다.");

    }
}

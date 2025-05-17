package com.zipte.platform.server.adapter.in.web;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.core.response.pageable.PageRequest;
import com.zipte.platform.core.response.pageable.PageResponse;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.adapter.in.web.dto.request.AnswerRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.QuestionAnswerDetailResponse;
import com.zipte.platform.server.adapter.in.web.dto.request.QuestionRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.QuestionAnswerListResponse;
import com.zipte.platform.server.application.in.community.AnswerUseCase;
import com.zipte.platform.server.application.in.community.QuestionUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class QaApi {

    /// 질문
    private final QuestionUseCase questionService;

    /// 답변
    private final AnswerUseCase answerService;

    @PostMapping("/question")
    public ApiResponse<String> createQuestion(
            @RequestBody @Valid QuestionRequest request,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        /// UserId 넣기
        request.setUserId(principalDetails.getId());

        questionService.createQuestion(request);

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

    @DeleteMapping("/question/{questionId}")
    public ApiResponse<String> deleteQuestion(
            @PathVariable Long questionId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        questionService.deleteQuestion(questionId, principalDetails.getId());

        return ApiResponse.ok("성공적으로 질문이 삭제되었습니다.");
    }


    @PostMapping("/answer")
    public ApiResponse<String> createAnswer(
            @RequestBody @Valid AnswerRequest request,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        /// UserId
        request.setUserId(principalDetails.getId());

        answerService.createAnswer(request);

        return ApiResponse.ok("성공적으로 답변이 저장되었습니다.");
    }

    @DeleteMapping("/answer/{answerId}")
    public ApiResponse<String> deleteAnswer(
            @PathVariable Long answerId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        answerService.deleteAnswer(answerId, principalDetails.getId());

        return ApiResponse.ok("성공적으로 질문이 삭제되었습니다.");
    }


}

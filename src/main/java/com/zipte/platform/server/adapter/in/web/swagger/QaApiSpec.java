package com.zipte.platform.server.adapter.in.web.swagger;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.core.response.pageable.PageRequest;
import com.zipte.platform.core.response.pageable.PageResponse;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.adapter.in.web.dto.request.AnswerRequest;
import com.zipte.platform.server.adapter.in.web.dto.request.QuestionRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.QuestionAnswerDetailResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.QuestionAnswerListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Q&A API", description = "Q&A 관련 API")
public interface QaApiSpec {

    @Operation(
            summary = "질문 등록",
            description = "JWT 를 통해서 사용자가 질문을 등록합니다.",
            security = @SecurityRequirement(name = "JWT"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(name = "질문 등록 예시", value = QUESTION_SUCCESS_PAYLOAD),
                            }
                    )
            )
    )
    ApiResponse<String> createQuestion(
            @RequestBody @Valid QuestionRequest request,
            @Parameter(hidden = true) PrincipalDetails principalDetails);



    @Operation(
            summary = "질문 목록 조회",
            description = "아파트 코드(kaptCode)를 기준으로 질문 목록을 조회합니다."
    )
    ApiResponse<PageResponse<QuestionAnswerListResponse>> getQuestions(
            @Parameter(description = "아파트 코드", example = "A46393018")
            @PathVariable String kaptCode,
            PageRequest pageRequest);


    @Operation(
            summary = "질문 상세 조회",
            description = "질문 ID를 통해 상세 정보를 조회합니다."
    )
    ApiResponse<QuestionAnswerDetailResponse> getQuestion(
            @Parameter(description = "질문 Id", example = "1")
            @PathVariable Long questionId);


    @Operation(
            summary = "질문 삭제",
            description = "질문 ID를 통해 사용자가 등록한 질문을 삭제합니다.",
            security = @SecurityRequirement(name = "JWT")
    )
    ApiResponse<String> deleteQuestion(
            @Parameter(description = "질문 Id", example = "1")
            @PathVariable Long questionId,
            @Parameter(hidden = true) PrincipalDetails principalDetails);


    @Operation(
            summary = "답변 등록",
            description = "JWT 를 통해서 사용자가 질문에 답변을 등록합니다.",
            security = @SecurityRequirement(name = "JWT"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(name = "질문 등록 예시", value = ANSWER_SUCCESS_PAYLOAD),
                            }
                    )
            )
    )
    ApiResponse<String> createAnswer(
            @RequestBody @Valid AnswerRequest request,
            @Parameter(hidden = true) PrincipalDetails principalDetails);



    @Operation(
            summary = "답변 삭제",
            description = "JWT 를 통해서 답변 ID를 통해 사용자가 등록한 답변을 삭제합니다.",
            security = @SecurityRequirement(name = "JWT")
    )
    ApiResponse<String> deleteAnswer(
            @Parameter(description = "답변 Id", example = "1")
            @PathVariable Long answerId,
            @Parameter(hidden = true) PrincipalDetails principalDetails);


    String QUESTION_SUCCESS_PAYLOAD = """
                {
                    "kaptCode" : "A46393018",
                    "title": "살기 좋나요?",
                    "content":"장미동부 아파트는 살기 좋나요?"
                }
            """;

    String ANSWER_SUCCESS_PAYLOAD = """
                {
                    "questionId":1,
                    "content": "살기 좋아요!"
                }
            """;



}

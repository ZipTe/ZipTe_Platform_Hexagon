package com.zipte.platform.server.adapter.in.web.swagger;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.adapter.in.web.dto.request.UserWeightRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.UserWeightResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 가중치 API", description = "AI를 위한 유저 가중치 관련 API")
public interface UserWeightApiSpec {

    @Operation(
            summary = "유저 가중치 설정",
            description = "JWT 토큰을 기반으로 가중치를 설정합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(name = "나의 가중치 등록 예시", value = SUCCESS_PAYLOAD),
                            }
                    )
            )
    )
    ApiResponse<String> create(@AuthenticationPrincipal PrincipalDetails principalDetails,
                               @RequestBody @Valid UserWeightRequest request);

    @Operation(
            summary = "유저 가중치 조회",
            description = "JWT 토큰을 기반으로 나의 가중치 현황를 조회합니다."
    )
    ApiResponse<UserWeightResponse> getMyWeight(
            @AuthenticationPrincipal PrincipalDetails principalDetails);

    @Operation(
            summary = "유저 가중치 삭제",
            description = "JWT 토큰을 기반으로 가중치를 삭제합니다."
    )
    ApiResponse<String> delete(
            @AuthenticationPrincipal PrincipalDetails principalDetails,

            @Parameter(required = true, description = "가중치의 ID", example = "1")
            @PathVariable Long id);


    String SUCCESS_PAYLOAD = """
            {
               "convenience": 4,
               "transportation": 5,
               "regionPreference": 3,
               "parkAccess": 2,
               "distanceToWork": 5
             }
            
            """;

}

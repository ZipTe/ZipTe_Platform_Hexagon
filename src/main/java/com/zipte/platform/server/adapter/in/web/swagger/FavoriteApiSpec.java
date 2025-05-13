package com.zipte.platform.server.adapter.in.web.swagger;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.core.response.pageable.PageRequest;
import com.zipte.platform.core.response.pageable.PageResponse;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.adapter.in.web.dto.request.FavoriteRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.FavoriteResponse;
import com.zipte.platform.server.domain.favorite.FavoriteType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "관심목록 API", description = "관심 목록 관련 API")
public interface FavoriteApiSpec {

    @Operation(
            summary = "관심 목록 추가",
            description = "JWT 토큰을 기반으로 지역 코드 또는 아파트 코드를 통해 관심 목록에 항목을 추가합니다.",
            security = @SecurityRequirement(name = "JWT"),
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(name = "아파트 관심 등록 예시", value = KAPT_PAYLOAD),
                                    @ExampleObject(name = "지역 관심 등록 예시", value = REGION_PAYLOAD)
                            }
                    )
            )
    )
    ApiResponse<String> createFavorite(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody @Valid FavoriteRequest request);


    @Operation(
            summary = "관심 목록 조회",
            description = "JWT 토큰을 기반으로 사용자의 관심 목록을 조회합니다. (type 미지정 시 전체 조회)",
            security = @SecurityRequirement(name = "JWT")
    )
    ApiResponse<PageResponse<FavoriteResponse>> loadFavorites(
            @AuthenticationPrincipal PrincipalDetails principalDetails,

            @Parameter(description = "관심목록 타입 설정")
            @RequestParam(required = false) FavoriteType type,
            PageRequest pageRequest
    );


    @Operation(
            summary = "관심 목록 삭제",
            description = "JWT 토큰을 기반으로 사용자의 관심 목록을 삭제합니다.",
            security = @SecurityRequirement(name = "JWT")
    )
    ApiResponse<String> deleteFavorite(

            @AuthenticationPrincipal PrincipalDetails principalDetails,

            @Parameter(description = "삭제할 관심목록 id", example = "1")
            @PathVariable Long id);


    /// 값

    String KAPT_PAYLOAD = """
        {
          "userId": 1,
          "type": "APARTMENT",
          "code": "A46378823"
        }
        """;

    String REGION_PAYLOAD = """
        {
          "userId": 1,
          "type": "REGION",
          "code": "4113000000"
        }
        """;
}

package com.zipte.platform.server.adapter.in.web.swagger;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.adapter.in.web.dto.request.EstateOwnershipRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "거주지 인증 API", description = "거주지 인증 관련 API")
public interface EstateOwnerShipApiSpec {

    @Operation(
            summary = "주거지 인증",
            description = "위치를 기반으로 주거지 인증을 진행합니다",
            security = @SecurityRequirement(name = "JWT"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(name = "주거지 인증 성공 예시", value = SUCCESS_PAYLOAD),
                                    @ExampleObject(name = "주거지 인증 실패 예시", value = FAIL_PAYLOAD)
                            }
                    )
            )
    )
    ApiResponse<String> create(
            @AuthenticationPrincipal PrincipalDetails principalDetails,

            @RequestBody EstateOwnershipRequest request);

    String SUCCESS_PAYLOAD = """
            {
              "kaptCode": "A46378823",
              "longitude": 127.127505625045,
              "latitude": 37.4154794825084,
              "boughtAt": "2023-10-15T14:30:00"
            }
            """;

    String FAIL_PAYLOAD = """
            {
               "kaptCode": "A46378823",
               "longitude": 130.9784,
               "latitude": 40.5665,
               "boughtAt": "2023-10-15T14:30:00"
            }
            """;



}

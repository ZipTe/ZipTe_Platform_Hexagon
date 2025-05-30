package com.zipte.platform.server.adapter.in.web.swagger;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.adapter.in.web.dto.response.EstateDetailResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.EstatePriceListResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.RegionPriceResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.RegionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@Tag(name = "대시보드 API", description = "대시보드 관련 API")
public interface DashBoardApiSpec {

    @Operation(
            summary = "나의 관심 지역 목록 조회",
            description = "JWT 를 통해서 나의 관심 지역 목록을 조회합니다."
    )
    ApiResponse<List<RegionResponse>> getMyRegion(
            @AuthenticationPrincipal PrincipalDetails principalDetails);


    @Operation(
            summary = "나의 관심 지역 가격 조회",
            description = "JWT 를 통해서 나의 관심 지역 가격을 조회합니다. (그래프 생성 데이터 전달)"
    )
    ApiResponse<List<RegionPriceResponse>> getMyRegionPrice(
            @AuthenticationPrincipal PrincipalDetails principalDetails);


    @Operation(
            summary = "나의 관심 아파트 목록 조회",
            description = "JWT 를 통해서 나의 관심 아파트 목록을 조회합니다."
    )
    ApiResponse<List<EstateDetailResponse>> getMyEstate(
            @AuthenticationPrincipal PrincipalDetails principalDetails);


    @Operation(
            summary = "나의 관심 아파트 가격 조회",
            description = "JWT 를 통해서 나의 관심 아파트 가격을 조회합니다. (그래프 생성 데이터 전달)"
    )
    ApiResponse<List<EstatePriceListResponse>> getMyEstatePrice(
            @AuthenticationPrincipal PrincipalDetails principalDetails);



    @Operation(
            summary = "나의 아파트 조회",
            description = "JWT 를 통해서 나의 거주 인증 아파트를 조회합니다. (그래프 생성 데이터 전달)"
    )
    ApiResponse<List<EstatePriceListResponse>> getMyHomeEstate(
            @AuthenticationPrincipal PrincipalDetails principalDetails);


}

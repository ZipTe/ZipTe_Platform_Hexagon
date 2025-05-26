package com.zipte.platform.server.adapter.in.web;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.adapter.in.web.dto.response.*;
import com.zipte.platform.server.application.in.dashboard.DashBoardUseCase;
import com.zipte.platform.server.domain.estate.Estate;
import com.zipte.platform.server.domain.estate.EstatePrice;
import com.zipte.platform.server.domain.region.Region;
import com.zipte.platform.server.domain.region.RegionPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashBoardApi {

    private final DashBoardUseCase dashboardService;

    @GetMapping("/region")
    public ApiResponse<List<RegionResponse>> getMyRegion(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        /// 유저 아이디 추출하기 -> AOP로 한번에 처리하기?
        Long userId = principalDetails.getId();

        List<Region> region = dashboardService.getFavoriteRegion(userId);

        List<RegionResponse> responses = RegionResponse.from(region);
        return ApiResponse.ok(responses);
    }


    @GetMapping("/region/price")
    public ApiResponse<List<RegionPriceResponse>> getMyRegionPrice(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        /// 유저 아이디 추출하기
        Long userId = principalDetails.getId();

        List<RegionPrice> regionPrices = dashboardService.getFavoriteRegionPrices(userId);

        List<RegionPriceResponse> responses = RegionPriceResponse.from(regionPrices);

        return ApiResponse.ok(responses);
    }


    @GetMapping("/estate")
    public ApiResponse<List<EstateDetailResponse>> getMyEstate(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        /// 유저 아이디 추출하기
        Long userId = principalDetails.getId();

        List<Estate> estates = dashboardService.getFavoriteEstates(userId);

        List<EstateDetailResponse> responses = EstateDetailResponse.from(estates);

        return ApiResponse.ok(responses);
    }

    @GetMapping("/estate/price")
    public ApiResponse<List<EstatePriceListResponse>> getMyEstatePrice(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        /// 유저 아이디 추출하기
        Long userId = principalDetails.getId();

        /// 서비스 계층 추출
        List<EstatePrice> estatePrices = dashboardService.getFavoriteEstatePrice(userId);

        List<EstatePriceListResponse> responses = EstatePriceListResponse.from(estatePrices);

        return ApiResponse.ok(responses);
    }

    @GetMapping("/estate/myhome")
    public ApiResponse<List<EstatePriceListResponse>> getMyHomeEstate(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        /// 유저 아이디 추출하기
        Long userId = principalDetails.getId();

        /// 서비스 계층 추출
        List<EstatePrice> myEstatePrices = dashboardService.getMyEstatePrices(userId);

        ///
        List<EstatePriceListResponse> responses = EstatePriceListResponse.from(myEstatePrices);

        return ApiResponse.ok(responses);
    }



}

package com.zipte.platform.server.adapter.in.web;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.server.adapter.in.web.dto.request.FavoriteRequest;
import com.zipte.platform.server.application.in.favorite.FavoriteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/favorite")
@RequiredArgsConstructor
public class FavoriteApi {

    private final FavoriteUseCase favoriteService;

    /// 관심 목록 추가하기
    @PostMapping()
    public ApiResponse<String> createFavorite(@RequestBody FavoriteRequest request) {

        /// 객체 생성
        favoriteService.createFavorite(request.getUserId(), request.getType(), request.getCode());

        return ApiResponse.ok("즐겨찾기가 정상적으로 추가되었습니다.");
    }


}

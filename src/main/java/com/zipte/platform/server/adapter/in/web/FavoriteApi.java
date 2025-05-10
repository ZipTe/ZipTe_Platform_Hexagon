package com.zipte.platform.server.adapter.in.web;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.core.response.pageable.PageRequest;
import com.zipte.platform.core.response.pageable.PageResponse;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.adapter.in.web.dto.request.FavoriteRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.FavoriteResponse;
import com.zipte.platform.server.application.in.favorite.FavoriteUseCase;
import com.zipte.platform.server.domain.favorite.Favorite;
import com.zipte.platform.server.domain.favorite.FavoriteType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorite")
@RequiredArgsConstructor
public class FavoriteApi {

    private final FavoriteUseCase favoriteService;

    /// 관심 목록 추가하기
    @PostMapping()
    public ApiResponse<String> createFavorite(@RequestBody @Valid FavoriteRequest request) {

        /// 객체 생성
        favoriteService.createFavorite(request);

        return ApiResponse.ok("즐겨찾기가 정상적으로 추가되었습니다.");
    }

    /// 관심 목록 해제하기
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteFavorite(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long id) {

        favoriteService.removeFavorite(principalDetails.getId(), id);

        return ApiResponse.ok("즐겨찾기가 정상적으로 삭제되었습니다.");
    }

    /// 나의 관심 목록 조회하기
    @GetMapping
    public ApiResponse<PageResponse<FavoriteResponse>> loadFavorites(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(required = false) FavoriteType type,
            PageRequest pageRequest) {

        // 페이징 처리
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        Page<Favorite> favoritePage;

        // 타입 조건에 따라 서비스 호출 분기
        if (type == FavoriteType.REGION || type == FavoriteType.APARTMENT) {
            favoritePage = favoriteService.getFavoriteByType(principalDetails.getId(), type, pageable);
        } else {
            favoritePage = favoriteService.getFavorite(principalDetails.getId(), pageable);
        }

        // dto 변환
        List<FavoriteResponse> list = FavoriteResponse.from(favoritePage.getContent());

        return ApiResponse.ok(new PageResponse<>(list, pageRequest, favoritePage.getTotalElements()));
    }

}

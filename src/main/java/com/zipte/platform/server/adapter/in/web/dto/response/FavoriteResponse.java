package com.zipte.platform.server.adapter.in.web.dto.response;

import com.zipte.platform.server.domain.favorite.Favorite;
import com.zipte.platform.server.domain.favorite.FavoriteType;
import lombok.Builder;

import java.util.List;

@Builder
public record FavoriteResponse(Long userId, FavoriteType type, String code) {


    public static FavoriteResponse from(Favorite favorite) {

        if (favorite.getType().equals(FavoriteType.APARTMENT)) {
            return FavoriteResponse.builder()
                    .userId(favorite.getUserId())
                    .type(favorite.getType())
                    .code(favorite.getKaptCode())
                    .build();
        }

        if (favorite.getType().equals(FavoriteType.REGION)) {
            return FavoriteResponse.builder()
                    .userId(favorite.getUserId())
                    .type(favorite.getType())
                    .code(favorite.getRegionCode())
                    .build();
        }

        return null;
    }

    public static List<FavoriteResponse> from(List<Favorite> favorites) {
        return favorites.stream()
                .map(FavoriteResponse::from)
                .toList();
    }

}

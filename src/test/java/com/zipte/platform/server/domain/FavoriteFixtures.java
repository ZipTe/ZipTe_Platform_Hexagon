package com.zipte.platform.server.domain;

import com.zipte.platform.server.domain.favorite.Favorite;
import com.zipte.platform.server.domain.favorite.FavoriteType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class FavoriteFixtures {

    public static Favorite apartmentFavorite(Long userId, String kaptCode) {
        return Favorite.of(userId, FavoriteType.APARTMENT, kaptCode);
    }

    public static Favorite regionFavorite(Long userId, String regionCode) {
        return Favorite.of(userId, FavoriteType.REGION, regionCode);
    }

    public static Favorite defaultApartmentFavorite_delete() {
        return Favorite.builder()
                .id(1L)
                .type(FavoriteType.APARTMENT)
                .userId(1L)
                .kaptCode(UUID.randomUUID().toString())
                .regionCode(null)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Favorite defaultRegionFavorite() {
        return Favorite.of(1L, FavoriteType.REGION, "1168010300");
    }

    public static Page<Favorite> defaultMixedFavoritePage() {
        List<Favorite> favorites = List.of(
                Favorite.of(1L, FavoriteType.APARTMENT, "kaptCode"),
                Favorite.of(1L, FavoriteType.REGION, "region")
        );

        return new PageImpl<>(favorites, PageRequest.of(0, 10), favorites.size());
    }

    public static Page<Favorite> defaultApartmentFavoritePage() {
        List<Favorite> favorites = List.of(
                Favorite.of(1L, FavoriteType.APARTMENT, "kaptCode1"),
                Favorite.of(1L, FavoriteType.APARTMENT, "kaptCode2"),
                Favorite.of(1L, FavoriteType.APARTMENT, "kaptCode3")
        );

        return new PageImpl<>(favorites, PageRequest.of(0, 10), favorites.size());
    }

    public static Page<Favorite> defaultRegionFavoritePage() {
        List<Favorite> favorites = List.of(
                Favorite.of(1L, FavoriteType.REGION, "region1"),
                Favorite.of(1L, FavoriteType.REGION, "region2"),
                Favorite.of(1L, FavoriteType.REGION, "region3"),
                Favorite.of(1L, FavoriteType.REGION, "region4")
        );

        return new PageImpl<>(favorites, PageRequest.of(0, 10), favorites.size());
    }



}

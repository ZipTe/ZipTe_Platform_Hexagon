package com.zipte.platform.server.adapter.out.kafka.event;

import com.zipte.platform.server.domain.favorite.Favorite;
import com.zipte.platform.server.domain.favorite.FavoriteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteEvent {

    /*
        Kafka로 받을 FavoriteEvent 객체
     */

    private EventType type;
    private Long userId;
    private FavoriteType favoriteType;
    private String complexCode;
    private String regionCode;
    private LocalDateTime occurredAt;

    /// 생성자
    public static FavoriteEvent from(EventType type, Favorite favorite) {

        return FavoriteEvent.builder()
                .type(type)
                .userId(favorite.getUserId())
                .favoriteType(favorite.getType())
                .complexCode(favorite.getKaptCode())
                .regionCode(favorite.getRegionCode())
                .occurredAt(favorite.getCreatedAt())
                .build();
    }

}

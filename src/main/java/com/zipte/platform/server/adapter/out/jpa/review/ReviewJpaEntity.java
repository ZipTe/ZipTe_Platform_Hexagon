package com.zipte.platform.server.adapter.out.jpa.review;

import com.zipte.platform.server.adapter.out.jpa.BaseEntity;
import com.zipte.platform.server.domain.review.Review;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
public class ReviewJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String kaptCode;

    private String title;

    private String content;

    private String imageUrl;

    private int facilities;

    private int infrastructure;

    private int neighborhood;

    private boolean certified;

    /// 정적 팩토리 메서드
    public static ReviewJpaEntity from(Review review) {
        return ReviewJpaEntity.builder()
                .id(review.getId())
                .userId(review.getUserId())
                .kaptCode(review.getKaptCode())
                .title(review.getTitle())
                .content(review.getContent())
                .imageUrl(review.getImageUrl())
                .facilities(review.getFacilities())
                .infrastructure(review.getInfrastructure())
                .neighborhood(review.getNeighborhood())
                .certified(review.isCertified())
                .build();
    }

    /// toDomain
    public Review toDomain() {
        return Review.builder()
                .id(id)
                .userId(userId)
                .kaptCode(kaptCode)
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .facilities(facilities)
                .infrastructure(infrastructure)
                .neighborhood(neighborhood)
                .certified(certified)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

}

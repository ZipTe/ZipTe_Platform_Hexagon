package com.zipte.platform.server.adapter.out.jpa.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewJpaRepository extends JpaRepository<ReviewJpaEntity, Long> {

    // 멤버별 리뷰 조회 (페이징)
    Page<ReviewJpaEntity> findByUserId(Long userId, Pageable pageable);

    // 아파트별 리뷰 조회 (페이징)
    Page<ReviewJpaEntity> findByKaptCode(String kaptCode, Pageable pageable);

    // 아파트별 리뷰 (전체 평점 높은 순, 페이징)
    Page<ReviewJpaEntity> findByKaptCodeOrderBySnippetOverallDesc(String kaptCode, Pageable pageable);


    /// 삭제를 위해서 유저, id가 동일한 것 가져오기
    boolean existsByIdAndUserId(Long id, Long userId);

    boolean existsByUserIdAndKaptCode(Long userId, String kaptCode);

}

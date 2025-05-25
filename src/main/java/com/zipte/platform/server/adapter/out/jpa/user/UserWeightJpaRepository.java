package com.zipte.platform.server.adapter.out.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserWeightJpaRepository extends JpaRepository<UserWeightJpaEntity, Long> {

    Optional<UserWeightJpaEntity> findByUserId(Long userId);

}

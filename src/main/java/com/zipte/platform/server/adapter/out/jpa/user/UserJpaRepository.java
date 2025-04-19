package com.zipte.platform.server.adapter.out.jpa.user;

import com.zipte.platform.server.domain.user.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    Optional<UserJpaEntity> findBySocialAndSocialId(OAuthProvider social, String socialId);

    boolean existsBySocialAndSocialId(OAuthProvider social, String socialId);


}

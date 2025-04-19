package com.zipte.platform.security.jwt.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JwtTokenRedisRepository extends CrudRepository<JwtToken, String> {

    Optional<JwtToken> findByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);

}

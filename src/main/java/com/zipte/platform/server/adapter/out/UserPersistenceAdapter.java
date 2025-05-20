package com.zipte.platform.server.adapter.out;

import com.zipte.platform.core.response.ErrorCode;
import com.zipte.platform.server.adapter.out.jpa.user.UserJpaEntity;
import com.zipte.platform.server.adapter.out.jpa.user.UserJpaRepository;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.user.OAuthProvider;
import com.zipte.platform.server.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPort {

    private final UserJpaRepository repository;

    @Override
    public User saveUser(User user) {
        var entity = UserJpaEntity.from(user);

        return repository.save(entity)
                .toDomain();
    }

    @Override
    /// 더티 체킹 활용
    public User updateUser(User user) {
        UserJpaEntity entity = repository.findById(user.getId())
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_USER.getMessage()));

        entity.changeUser(user);

        return entity.toDomain();
    }

    @Override
    public boolean checkExistingBySocialAndSocialId(OAuthProvider social, String socialId ) {

        return repository.existsBySocialAndSocialId(social, socialId);
    }

    @Override
    public boolean checkExistingById(Long userId) {
        return repository.existsById(userId);
    }

    @Override
    public Optional<User> loadUserById(Long userId) {
        return repository.findById(userId)
                .map(UserJpaEntity::toDomain);
    }

    @Override
    public Optional<User> loadUserBySocialAndSocialId(OAuthProvider social, String socialId) {
        return repository.findBySocialAndSocialId(social, socialId)
                .map(UserJpaEntity::toDomain);
    }

}

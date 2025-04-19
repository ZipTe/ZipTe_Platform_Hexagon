package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.jpa.user.UserJpaEntity;
import com.zipte.platform.server.adapter.out.jpa.user.UserJpaRepository;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.user.OAuthProvider;
import com.zipte.platform.server.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
    public User updateUser(User user) {
        return null;
    }

    @Override
    public boolean checkExistingBySocialAndSocialId(OAuthProvider social, String socialId ) {

        return repository.existsBySocialAndSocialId(social, socialId);
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

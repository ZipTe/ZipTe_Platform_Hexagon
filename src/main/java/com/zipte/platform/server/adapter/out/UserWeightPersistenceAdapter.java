package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.jpa.user.UserWeightJpaEntity;
import com.zipte.platform.server.adapter.out.jpa.user.UserWeightJpaRepository;
import com.zipte.platform.server.application.out.user.UserWeightPort;
import com.zipte.platform.server.domain.user.UserWeight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserWeightPersistenceAdapter implements UserWeightPort {

    private final UserWeightJpaRepository repository;

    @Override
    public UserWeight saveUserWeight(UserWeight userWeight) {
        var entity = UserWeightJpaEntity.from(userWeight);

        return repository.save(entity)
                .toDomain();
    }

    @Override
    public Optional<UserWeight> loadUserWeightByUserId(Long userId) {
        return repository.findByUserId(userId)
                .map(UserWeightJpaEntity::toDomain);
    }

    @Override
    public void deleteUserWeight(Long weightId) {
        repository.deleteById(weightId);
    }
}

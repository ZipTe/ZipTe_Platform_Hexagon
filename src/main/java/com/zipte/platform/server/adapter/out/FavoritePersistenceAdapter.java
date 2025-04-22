package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.jpa.favorite.FavoriteJpaRepository;
import com.zipte.platform.server.adapter.out.jpa.favorite.FavoriteJpaEntity;
import com.zipte.platform.server.application.out.favorite.FavoritePort;
import com.zipte.platform.server.domain.favorite.Favorite;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FavoritePersistenceAdapter implements FavoritePort {

    private final FavoriteJpaRepository repository;

    @Override
    public Favorite saveFavorite(Favorite favorite) {
        var entity = FavoriteJpaEntity.from(favorite);

        return repository.save(entity)
                .toDomain();
    }

    @Override
    public void deleteFavorite(Long favoriteId) {
        repository.deleteById(favoriteId);
    }

    @Override
    public Page<Favorite> loadUserPreferences(Long userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable)
                .map(FavoriteJpaEntity::toDomain);
    }

    @Override
    public List<Long> loadUserFavoriteByComplexCode(String kaptCode) {
        return repository.findUserIdsByKaptCode(kaptCode).stream()
                .map(FavoriteJpaEntity::getUserId)
                .toList();
    }


}

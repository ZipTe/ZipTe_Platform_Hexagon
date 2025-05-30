package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.jpa.favorite.FavoriteJpaRepository;
import com.zipte.platform.server.adapter.out.jpa.favorite.FavoriteJpaEntity;
import com.zipte.platform.server.application.out.favorite.FavoritePort;
import com.zipte.platform.server.domain.favorite.Favorite;
import com.zipte.platform.server.domain.favorite.FavoriteType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
    public Optional<Favorite> loadFavoriteById(Long id) {
        return repository.findById(id)
                .map(FavoriteJpaEntity::toDomain);
    }

    @Override
    public Optional<Favorite> loadFavoriteByIdAndUserId(Long id, Long userId) {
        return Optional.empty();
    }

    @Override
    public void deleteFavorite(Long favoriteId) {
        repository.deleteById(favoriteId);
    }

    @Override
    public Page<Favorite> loadUserFavorite(Long userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable)
                .map(FavoriteJpaEntity::toDomain);
    }

    @Override
    public Page<Favorite> loadUserFavoriteByType(Long userId, FavoriteType type, Pageable pageable) {
        return repository.findByUserIdAndType(userId, type, pageable)
                .map(FavoriteJpaEntity::toDomain);
    }

    @Override
    public List<Favorite> loadUserFavoriteByType(Long userId, FavoriteType type) {
        return repository.findByUserIdAndType(userId, type).stream()
                .map(FavoriteJpaEntity::toDomain)
                .toList();
    }

    @Override
    public boolean checkFavoriteByUserIdAndTypeAndCode(Long userId, FavoriteType type, String code) {
        if (type.equals(FavoriteType.REGION)) {
            return repository.existsByUserIdAndRegionCode(userId, code);
        }

        else if (type.equals(FavoriteType.APARTMENT)) {
            return repository.existsByUserIdAndKaptCode(userId, code);
        }

        return false;
    }

    @Override
    public List<Long> loadUserFavoriteByComplexCode(String kaptCode) {
        return repository.findUserIdsByKaptCode(kaptCode).stream()
                .map(FavoriteJpaEntity::getUserId)
                .toList();
    }


}

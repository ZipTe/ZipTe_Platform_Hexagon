package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.in.web.dto.request.PropertyConditionRequest;
import com.zipte.platform.server.adapter.out.jpa.property.PropertyJpaEntity;
import com.zipte.platform.server.adapter.out.jpa.property.PropertyJpaEntityRepository;
import com.zipte.platform.server.application.out.property.DeletePropertyPort;
import com.zipte.platform.server.application.out.property.LoadPropertyPort;
import com.zipte.platform.server.application.out.property.SavePropertyPort;
import com.zipte.platform.server.domain.property.Property;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PropertyPersistenceAdapter implements LoadPropertyPort, SavePropertyPort, DeletePropertyPort {

    private final PropertyJpaEntityRepository repository;

    @Override
    public Property saveProperty(Property property) {

        var entity = PropertyJpaEntity.from(property);
        return repository.save(entity)
                .toDomain();
    }

    @Override
    public Optional<Property> loadProperty(Long id) {
        return repository.findById(id)
                .map(PropertyJpaEntity::toDomain);
    }

    @Override
    public Page<Property> loadList(Pageable pageable) {
        return repository.findAllByOrderByCreatedAtDesc(pageable)
                .map(PropertyJpaEntity::toDomain);

    }

    @Override
    public Page<Property> loadListByLikeCount(Pageable pageable) {
        return repository.findAllOrderByLikeCount(pageable)
                .map(PropertyJpaEntity::toDomain);
    }

    @Override
    public Page<Property> loadListByViewCount(Pageable pageable) {
        return repository.findAllOrderByViewCount(pageable)
                .map(PropertyJpaEntity::toDomain);
    }

    @Override
    public Page<Property> loadListByCondition(Pageable pageable, PropertyConditionRequest request) {
        return null;
    }

    @Override
    public void deleteProperty(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsPropertyBySellerId(Long sellerId) {
        return repository.existsByOwnerId(sellerId);
    }

    @Override
    public Optional<Property> loadPropertyBySellerIdAndPropertyId(Long sellerId, Long propertyId) {
        return repository.findByOwnerIdAndId(sellerId, propertyId)
                .map(PropertyJpaEntity::toDomain);
    }

    @Override
    public int countPropertiesInEstate(String kaptCode) {
        return repository.countByKaptCode(kaptCode);
    }
}

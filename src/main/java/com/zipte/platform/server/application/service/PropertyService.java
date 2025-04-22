package com.zipte.platform.server.application.service;


import com.zipte.platform.server.adapter.in.web.dto.PropertyConditionRequest;
import com.zipte.platform.server.adapter.in.web.dto.PropertyRequest;
import com.zipte.platform.server.application.in.property.*;
import com.zipte.platform.server.application.in.task.PropertyNotificationTask;
import com.zipte.platform.server.application.out.estate.LoadEstatePort;
import com.zipte.platform.server.application.out.property.DeletePropertyPort;
import com.zipte.platform.server.application.out.property.LoadPropertyPort;
import com.zipte.platform.server.application.out.property.SavePropertyPort;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.estate.Estate;
import com.zipte.platform.server.domain.property.Property;
import com.zipte.platform.server.domain.property.PropertyAddress;
import com.zipte.platform.server.domain.property.PropertySnippet;
import com.zipte.platform.server.domain.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional

public class PropertyService implements CreatePropertyUseCase, GetPropertyDetailsUseCase ,DeletePropertyUseCase, UpdatePropertyUseCase, ListPropertiesUseCase {

    private final SavePropertyPort savePort;
    private final LoadPropertyPort loadPort;
    private final DeletePropertyPort deletePort;

    private final UserPort loadUserPort;
    private final LoadEstatePort loadEstatePort;

    // 알림센터 제작 의존성
    private final PropertyNotificationTask notificationService;

    @Override
    public Property create(PropertyRequest request) {

        /// 유저 예외성 체크
        User user = getUser(request.getUserId());

        /// 아파트 예외성 체크
        Estate estate = loadEstatePort.loadEstateByCode(request.getAptCode())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 아파트가 존재하지 않습니다."));

        /// 매물 생성
        PropertyAddress address = PropertyAddress.of(estate.getKaptAddr(), request.getDetailAddress(), request.getPostalCode());
        PropertySnippet snippet = PropertySnippet.of(estate.getKaptName(), request.getDescription(), request.getQuantity(), request.getBathrooms(), request.getBuiltYear());

        Property property = Property.of(user.getId(), request.getType(), address, snippet, request.getPrice(), request.getAptCode());

        /// 매물 저장
        Property savedProperty = savePort.saveProperty(property);

        /// 알림 저장
        notificationService.createNotification(savedProperty);

        return savedProperty;
    }

    @Override
    public void deleteProperty(Long id) {

        /// 알림 삭제
        Property property = getPropertyById(id);
        notificationService.removeNotification(property);

        /// 매물 삭제
        deletePort.deleteProperty(id);
    }

    @Override
    public Property getPropertyById(Long propertyId) {

        return loadPort.loadProperty(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("매물이 존재하지 않습니다."));

    }

    @Override
    public Page<Property> getList(Pageable pageable) {
        return loadPort.loadList(pageable);
    }

    @Override
    public Page<Property> getListByCondition(PropertyConditionRequest request, Pageable pageable) {
        return loadPort.loadListByCondition(pageable, request);
    }

    @Override
    public Page<Property> getListByLikeCount(Pageable pageable) {
        return loadPort.loadListByLikeCount(pageable);
    }

    @Override
    public Page<Property> getListByViewCount(Pageable pageable) {
        return loadPort.loadListByViewCount(pageable);
    }


    private User getUser(Long userId) {
        return loadUserPort.loadUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다"));
    }

}

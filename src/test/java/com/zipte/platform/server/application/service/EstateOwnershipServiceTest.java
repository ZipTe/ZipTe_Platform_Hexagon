package com.zipte.platform.server.application.service;

import com.zipte.platform.server.adapter.in.web.dto.request.EstateOwnershipRequest;
import com.zipte.platform.server.application.out.estate.LoadEstatePort;
import com.zipte.platform.server.application.out.estateOwnership.EstateOwnerShipPort;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.application.service.exception.AlreadyExistingEstateException;
import com.zipte.platform.server.application.service.exception.NotExistingEstateInYourAreaException;
import com.zipte.platform.server.domain.estate.Estate;
import com.zipte.platform.server.domain.estateOwnership.EstateOwnership;
import com.zipte.platform.server.domain.estate.EstateFixtures;
import com.zipte.platform.server.domain.review.EstateOwnerShipFixtures;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EstateOwnershipServiceTest {

    private final double EARTH_RADIUS_KM = 6373;
    private final double ONE_KM_IN_RADIANS = 1 / EARTH_RADIUS_KM;

    @Mock
    private EstateOwnerShipPort port;

    @Mock
    private LoadEstatePort loadEstatePort;

    @Mock
    private UserPort loadUserPort;

    @InjectMocks
    private EstateOwnershipService sut;

    @Test
    @DisplayName("[error] TC - 유저가 없는 경우")
    public void badRequest_user() {

        // Given
        var request = createRequest(00.00, 00.00);

        // When
        given(loadUserPort.checkExistingById(anyLong())).willReturn(false);

        // Then
        Assert.assertThrows(NoSuchElementException.class,
                () -> sut.createOwnership(request));

    }


    @Test
    @DisplayName("[error] TC - 아파트 코드가 없는 경운")
    public void badRequest_code() {

        // Given
        var request = createRequest(00.00, 00.00);

        // When
        given(loadUserPort.checkExistingById(anyLong())).willReturn(true);
        given(loadEstatePort.checkExistingByCode(any())).willReturn(false);

        // Then
        Assert.assertThrows(NoSuchElementException.class,
                () -> sut.createOwnership(request));


    }

    @Test
    @DisplayName("[happy] TC - 원하는 아파트 반경 1KM에 유저가 존재하는 경우 인증 가능")
    public void createOwnerShipBy1KM() {

        // Given
        var request = createRequest(37.12345, 127.123456);

        given(loadUserPort.checkExistingById(anyLong()))
                .willReturn(true);

        given(loadEstatePort.checkExistingByCode(any()))
                .willReturn(true);

        given(port.loadOwnershipByUser(request.getUserId(), request.getKaptCode()))
                .willReturn(false);

        Estate stub = EstateFixtures.stub(request.getKaptCode());

        given(loadEstatePort.loadEstatesNearBy(request.getLongitude(), request.getLatitude(), ONE_KM_IN_RADIANS))
                .willReturn(List.of(stub));

        given(port.saveOwnership(any(EstateOwnership.class)))
                .willReturn(EstateOwnerShipFixtures.stub(request.getUserId(), request.getKaptCode(), request.getBoughtAt()));

        // When
        var result = sut.createOwnership(request);

        // Then
        then(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("userId", 1L)
                .hasFieldOrPropertyWithValue("kaptCode", "kaptCode");
    }

    @Test
    @DisplayName("[edge] TC - 원하는 아파트 반경 1KM 외부에 유저가 존재하는 경우 인증 불가")
    public void createOwnerShipBy1KMOutOfRange() {

        // Given
        var request = createRequest(00.00, 00.00);


        // Mocking 서비스 메서드 호출
        given(loadUserPort.checkExistingById(anyLong())).willReturn(true);

        given(loadEstatePort.checkExistingByCode(any())).willReturn(true);


        // When & Then
        Assert.assertThrows(NotExistingEstateInYourAreaException.class,
                () -> sut.createOwnership(request));
    }


    @Test
    @DisplayName("[edge] TC - 인증 가능 케이스에서 오늘 이후의 날짜로 구매 날짜를 지정 IllegalStateException 발생")
    public void createOwnerShipBy1KM_dateError() {

        // Given
        var request = createRequest(00.00, 00.00, LocalDateTime.now().plusDays(3));

        given(loadUserPort.checkExistingById(anyLong()))
                .willReturn(true);
        given(loadEstatePort.checkExistingByCode(any()))
                .willReturn(true);

        // When & Then
        Assert.assertThrows(IllegalArgumentException.class,
                () -> sut.createOwnership(request));


    }



    private EstateOwnershipRequest createRequest(double lat, double lon) {
        return EstateOwnershipRequest.builder()
                .userId(1L)
                .kaptCode("kaptCode")
                .latitude(lat)
                .longitude(lon)
                .boughtAt(LocalDateTime.now().minusDays(1))
                .build();
    }

    private EstateOwnershipRequest createRequest(double lat, double lon, LocalDateTime boughtAt) {
        return EstateOwnershipRequest.builder()
                .userId(1L)
                .kaptCode("kaptCode")
                .latitude(lat)
                .longitude(lon)
                .boughtAt(boughtAt)
                .build();
    }

    @Test
    @DisplayName("[edge] TC - 이미 인증된 아파트에 대해 또 인증 요청 시 예외 발생")
    void duplicateOwnershipRequest() {
        // Given
        var request = createRequest(00.00, 00.00);

        given(loadUserPort.checkExistingById(anyLong())).willReturn(true);
        given(loadEstatePort.checkExistingByCode(any())).willReturn(true);

        // 중복 존재 시나리오
        given(port.loadOwnershipByUser(anyLong(), any()))
                .willReturn(true);

        // When & Then
        Assert.assertThrows(AlreadyExistingEstateException.class,
                () -> sut.createOwnership(request));
    }







}

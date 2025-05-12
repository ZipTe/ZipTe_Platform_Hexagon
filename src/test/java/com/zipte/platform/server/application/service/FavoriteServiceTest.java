package com.zipte.platform.server.application.service;

import com.zipte.platform.server.adapter.in.web.dto.request.FavoriteRequest;
import com.zipte.platform.server.application.out.estate.LoadEstatePort;
import com.zipte.platform.server.application.out.favorite.FavoritePort;
import com.zipte.platform.server.application.out.region.RegionPort;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.FavoriteFixtures;
import com.zipte.platform.server.domain.favorite.Favorite;
import com.zipte.platform.server.domain.favorite.FavoriteType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceTest {

    @Mock
    private FavoritePort favoritePort;

    @Mock
    private UserPort loadUserPort;

    @Mock
    private LoadEstatePort loadEstatePort;

    @Mock
    private RegionPort loadRegionPort;

    @InjectMocks
    private FavoriteService sut;

    /// 생성
    @Test
    @DisplayName("[happy]지역을 나의 관심목록에 추가한다.")
    public void createRegionFavorite() {

        // given
        var request = createRegionFavoriteRequest();

        given(loadUserPort.checkExistingById(request.getUserId()))
                .willReturn(true);

        given(loadRegionPort.checkExistCode(request.getCode()))
                .willReturn(true);

        given(favoritePort.saveFavorite(any()))
                .willReturn(FavoriteFixtures.regionFavorite(request.getUserId(), request.getCode()));

        // when
        var result = sut.createFavorite(request);

        // then
        then(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("userId", request.getUserId())
                .hasFieldOrPropertyWithValue("regionCode", request.getCode());
    }

    @Test
    @DisplayName("[happy]아파트를 나의 관심목록에 추가한다.")
    public void createKaptFavorite() {

        // given
        var request = createKaptFavoriteRequest();

        given(loadUserPort.checkExistingById(request.getUserId()))
                .willReturn(true);

        given(loadEstatePort.checkExistingByCode(request.getCode()))
                .willReturn(true);

        given(favoritePort.saveFavorite(any()))
                .willReturn(FavoriteFixtures.apartmentFavorite(request.getUserId(), request.getCode()));

        // when
        var result = sut.createFavorite(request);

        // then
        then(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("userId", request.getUserId())
                .hasFieldOrPropertyWithValue("kaptCode", request.getCode());
    }


    @Test
    @DisplayName("[edge] 이미 등록된 관심항목이면 예외가 발생한다.")
    void createFavorite_duplicateFavorite_throwsException() {
        // given
        var request = createRegionFavoriteRequest();

        given(loadUserPort.checkExistingById(request.getUserId())).willReturn(true);

        given(favoritePort.checkFavoriteByUserIdAndTypeAndCode(request.getUserId(), request.getType(), request.getCode()))
                .willReturn(true);

        // when & then
        assertThrows(IllegalStateException.class, () -> sut.createFavorite(request));
    }


    /// 에러 케이스
    @Test
    @DisplayName("[error]존재하지 않는 지역에 대해 접근하면 NoSuchElementException 에러가 발생한다. ")
    public void createRegionFavorite_region_NoSuchElementException() {

        // given
        var request = createRegionFavoriteRequest();

        given(loadUserPort.checkExistingById(request.getUserId()))
                .willReturn(true);

        given(loadRegionPort.checkExistCode(request.getCode()))
                .willReturn(false);

        // when & then
        assertThrows(NoSuchElementException.class,
                () -> sut.createFavorite(request));
    }

    @Test
    @DisplayName("[error]존재하지 않는 아파트에 대해 접근하면 NoSuchElementException 에러가 발생한다. ")
    public void createRegionFavorite_estate_NoSuchElementException() {

        // given
        var request = createKaptFavoriteRequest();

        given(loadUserPort.checkExistingById(request.getUserId()))
                .willReturn(true);

        given(loadEstatePort.checkExistingByCode(request.getCode()))
                .willReturn(false);

        // when & then
        assertThrows(NoSuchElementException.class,
                () -> sut.createFavorite(request));
    }

    @Test
    @DisplayName("[error]존재하지 않는 유저가 접근하면 NoSuchElementException 에러가 발생한다. ")
    public void createRegionFavorite_user_NoSuchElementException() {

        // given
        var request = createRegionFavoriteRequest();

        given(loadUserPort.checkExistingById(request.getUserId()))
                .willReturn(false);

        // when & then
        assertThrows(NoSuchElementException.class,
                () -> sut.createFavorite(request));
    }


    /// 조회
    @Test
    @DisplayName("[happy]나의 관심목록에서 타입과 관련없이 여러가지 목록을 조회한다..")
    public void loadMyFavorite() {

        // given
        Long userId = 1L;
        Pageable pageable = Pageable.ofSize(10);

        given(loadUserPort.checkExistingById(userId))
                .willReturn(true);

        given(favoritePort.loadUserFavorite(userId, pageable))
                .willReturn(FavoriteFixtures.defaultMixedFavoritePage());

        // when
        Page<Favorite> result = sut.getFavorite(userId, pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent())
                .extracting(Favorite::getType)
                .containsExactlyInAnyOrder(FavoriteType.APARTMENT, FavoriteType.REGION);
    }

    @Test
    @DisplayName("[happy]나의 관심목록에서 지역과 관련된 목록을 조회한다..")
    public void loadMyFavorite_Region() {

        // given
        Long userId = 1L;
        Pageable pageable = Pageable.ofSize(10);

        given(loadUserPort.checkExistingById(userId))
                .willReturn(true);

        given(favoritePort.loadUserFavorite(userId, pageable))
                .willReturn(FavoriteFixtures.defaultRegionFavoritePage());

        // when
        Page<Favorite> result = sut.getFavorite(userId, pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(4);
        assertThat(result.getContent()).hasSize(4);
        assertThat(result.getContent())
                .extracting(Favorite::getType)
                .contains(FavoriteType.REGION);

    }

    @Test
    @DisplayName("[happy]나의 관심목록에서 아파트와 관련된 목록을 조회한다.")
    public void loadMyFavorite_Kapt() {

        // given
        Long userId = 1L;
        Pageable pageable = Pageable.ofSize(10);

        given(loadUserPort.checkExistingById(userId))
                .willReturn(true);

        given(favoritePort.loadUserFavorite(userId, pageable))
                .willReturn(FavoriteFixtures.defaultApartmentFavoritePage());

        // when
        Page<Favorite> result = sut.getFavorite(userId, pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getContent()).hasSize(3);
        assertThat(result.getContent())
                .extracting(Favorite::getType)
                .contains(FavoriteType.APARTMENT);

    }

    /// 삭제
    @Test
    @DisplayName("[happy]나의 관심목록에서 아파트와 관련된 목록을 삭제한다.")
    void deleteFavorite() {

        // given
        Long userId = 1L;
        Long favoriteId = 1L;

        given(loadUserPort.checkExistingById(userId))
                .willReturn(true);

        given(favoritePort.loadFavoriteByIdAndUserId(userId, favoriteId))
                .willReturn(Optional.of(FavoriteFixtures.defaultApartmentFavorite_delete()));

        // when
        sut.removeFavorite(userId, favoriteId);

        // then
        verify(favoritePort).deleteFavorite(favoriteId);

    }

    @Test
    @DisplayName("[happy]관심 목록이 비어 있으면 빈 페이지가 반환된다.")
    void loadMyFavorite_emptyList() {
        // given
        Long userId = 1L;
        Pageable pageable = Pageable.ofSize(10);

        given(loadUserPort.checkExistingById(userId))
                .willReturn(true);

        given(favoritePort.loadUserFavorite(userId, pageable))
                .willReturn(Page.empty());

        // when
        Page<Favorite> result = sut.getFavorite(userId, pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.getContent()).isEmpty();
    }


    /// 유저가 존재하지 않는 경우
    @Test
    @DisplayName("[error]유저가 존재하지 않으면 NoSuchElementException을 발생시킨다.")
    void deleteFavorite_userNotExist_throwsException() {
        // given
        Long userId = 1L;
        Long favoriteId = 1L;

        given(loadUserPort.checkExistingById(userId))
                .willReturn(false);

        // when & then
        assertThrows(NoSuchElementException.class, () -> sut.removeFavorite(userId, favoriteId));
    }


    @Test
    @DisplayName("[error]존재하지 않는 관심 항목을 삭제하려고 하면 예외가 발생한다.")
    void deleteFavorite_nonExistentFavorite_throwsException() {

        // given
        Long userId = 1L;
        Long favoriteId = 1L;

        given(loadUserPort.checkExistingById(userId))
                .willReturn(true);

        given(favoritePort.loadFavoriteByIdAndUserId(userId, favoriteId))
                .willReturn(Optional.empty());

        // when & then
        assertThrows(NoSuchElementException.class,
                () -> sut.removeFavorite(userId, favoriteId));
    }

    @Test
    @DisplayName("[error] 다른 사용자가 자신의 관심 항목을 삭제하려 하면 NoSuchElementException 이 발생한다.")
    void deleteFavorite_otherUserTriesToDelete_throwsException() {
        // given
        Long ownerId = 1L;            // 실제 관심 항목 소유자
        Long favoriteId = 1L;
        Long attackerId = 2L;         // 삭제 시도자 (다른 사용자)

        // 삭제 시도자는 존재하는 사용자임
        given(loadUserPort.checkExistingById(attackerId))
                .willReturn(true);

        // favoriteId와 삭제 시도자의 ID로는 관심 항목이 조회되지 않음
        given(favoritePort.loadFavoriteByIdAndUserId(favoriteId, attackerId))
                .willReturn(Optional.empty());

        // when & then
        assertThrows(NoSuchElementException.class, () ->
                sut.removeFavorite(attackerId, favoriteId)
        );
    }




    // 내부 함수
    private static FavoriteRequest createKaptFavoriteRequest() {
        FavoriteRequest request = new FavoriteRequest();

        request.setUserId(1L);
        request.setType(FavoriteType.APARTMENT);
        request.setCode("kaptCode");

        return request;
    }

    // 내부 함수
    private static FavoriteRequest createRegionFavoriteRequest() {
        FavoriteRequest request = new FavoriteRequest();

        request.setUserId(1L);
        request.setType(FavoriteType.REGION);
        request.setCode("regionCode");

        return request;
    }

}

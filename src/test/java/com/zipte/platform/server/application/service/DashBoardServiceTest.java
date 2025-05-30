package com.zipte.platform.server.application.service;

import com.zipte.platform.server.application.out.estate.EstatePricePort;
import com.zipte.platform.server.application.out.estate.LoadEstatePort;
import com.zipte.platform.server.application.out.estateOwnership.EstateOwnerShipPort;
import com.zipte.platform.server.application.out.favorite.FavoritePort;
import com.zipte.platform.server.application.out.region.RegionPort;
import com.zipte.platform.server.application.out.region.RegionPricePort;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.FavoriteFixtures;
import com.zipte.platform.server.domain.estate.Estate;
import com.zipte.platform.server.domain.estate.EstatePrice;
import com.zipte.platform.server.domain.estateOwnership.EstateOwnership;
import com.zipte.platform.server.domain.favorite.Favorite;
import com.zipte.platform.server.domain.favorite.FavoriteType;
import com.zipte.platform.server.domain.region.Region;
import com.zipte.platform.server.domain.region.RegionFixtures;
import com.zipte.platform.server.domain.estate.EstateFixtures;
import com.zipte.platform.server.domain.review.EstateOwnerShipFixtures;
import com.zipte.platform.server.domain.estate.EstatePriceFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DashBoardServiceTest {

    @Mock
    private UserPort userPort;

    @Mock
    private FavoritePort favoritePort;

    @Mock
    private RegionPort regionPort;

    @Mock
    private RegionPricePort regionPricePort;

    @Mock
    private LoadEstatePort loadEstatePort;

    @Mock
    private EstatePricePort estatePricePort;

    @Mock
    private EstateOwnerShipPort estateOwnerShipPort;

    @InjectMocks
    private DashBoardService sut;


    @Nested
    @DisplayName("지역 관련 조회")
    class GetRegion {

        @Test
        @DisplayName("[happy] 나의 관심 지역 목록을 조회합니다.")
        void getRegionList_happy() {

            // Given
            Long userId = 1L;

            given(userPort.checkExistingById(userId))
                    .willReturn(true);

            Favorite 테스트_지역1 = FavoriteFixtures.regionFavorite(userId, "분당구");
            Favorite 테스트_지역2 = FavoriteFixtures.regionFavorite(userId, "종로구");
            List<Favorite> favoriteList = List.of(테스트_지역1, 테스트_지역2);

            given(favoritePort.loadUserFavoriteByType(userId, FavoriteType.REGION))
                    .willReturn(favoriteList);

            List<String> codes = List.of(테스트_지역1.getRegionCode(), 테스트_지역2.getRegionCode());

            Region 분당구 = RegionFixtures.분당구();
            Region 종로구 = RegionFixtures.종로구();
            List<Region> regionList = List.of(분당구, 종로구);

            given(regionPort.loadRegionsByCodes(codes))
                    .willReturn(regionList);

            // When
            List<Region> response = sut.getFavoriteRegion(userId);

            // Then
            then(response)
                    .isNotNull()
                    .hasSize(2)
                    .containsExactlyInAnyOrder(분당구, 종로구);
        }


        @Test
        @DisplayName("[happy] 나의 관심 지역 가격을 조회합니다.")
        void getRegionPrice_happy() {

            // Given
            Long userId = 1L;

            given(userPort.checkExistingById(userId))
                    .willReturn(true);

            Favorite 테스트_지역1 = FavoriteFixtures.regionFavorite(userId, "분당구");
            Favorite 테스트_지역2 = FavoriteFixtures.regionFavorite(userId, "종로구");
            List<Favorite> favoriteList = List.of(테스트_지역1, 테스트_지역2);

            given(favoritePort.loadUserFavoriteByType(userId, FavoriteType.REGION))
                    .willReturn(favoriteList);

            List<String> codes = List.of(테스트_지역1.getRegionCode(), 테스트_지역2.getRegionCode());

            Region 분당구 = RegionFixtures.분당구();
            Region 종로구 = RegionFixtures.종로구();
            List<Region> regionList = List.of(분당구, 종로구);

            given(regionPort.loadRegionsByCodes(codes))
                    .willReturn(regionList);

            // When
            List<Region> response = sut.getFavoriteRegion(userId);

            // Then
            then(response)
                    .isNotNull()
                    .hasSize(2)
                    .containsExactlyInAnyOrder(분당구, 종로구);
        }

    }

    @Nested
    @DisplayName("아파트 관련 조회")
    class EstateTest {

        @Test
        @DisplayName("[happy] 나의 관심 아파트 목록을 조회합니다.")
        void getFavoriteEstates_success() {
            // Given
            Long userId = 1L;
            String kaptCode1 = "KaptCode1";
            String kaptCode2 = "KaptCode2";
            given(userPort.checkExistingById(userId))
                    .willReturn(true);

            Favorite apt1 = FavoriteFixtures.apartmentFavorite(userId, kaptCode1);
            Favorite apt2 = FavoriteFixtures.apartmentFavorite(userId, kaptCode2);

            given(favoritePort.loadUserFavoriteByType(userId, FavoriteType.APARTMENT))
                    .willReturn(List.of(apt1, apt2));

            Estate stub1 = EstateFixtures.stub(kaptCode1);
            Estate stub2 = EstateFixtures.stub(kaptCode2);

            given(loadEstatePort.loadEstatesByCodes(List.of(kaptCode1, kaptCode2)))
                    .willReturn(List.of(stub1, stub2));

            // When
            var result = sut.getFavoriteEstates(userId);

            // Then
            then(result)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(stub1, stub2);
        }

        @Test
        @DisplayName("[happy] 나의 관심 아파트 가격을 조회합니다.")
        void getFavoriteEstatePrices_success() {
            // Given
            Long userId = 1L;
            given(userPort.checkExistingById(userId))
                    .willReturn(true);

            String kaptCode1 = "KaptCode1";
            String kaptCode2 = "KaptCode2";
            Favorite apt1 = FavoriteFixtures.apartmentFavorite(userId, kaptCode1);
            Favorite apt2 = FavoriteFixtures.apartmentFavorite(userId, kaptCode2);

            given(favoritePort.loadUserFavoriteByType(userId, FavoriteType.APARTMENT))
                    .willReturn(List.of(apt1, apt2));

            EstatePrice stub = EstatePriceFixtures.stub(kaptCode1);
            EstatePrice stub2 = EstatePriceFixtures.stub(kaptCode2);

            given(estatePricePort.loadEstatePricesByCodes(List.of(kaptCode1, kaptCode2)))
                    .willReturn(List.of(stub, stub2));

            // When
            List<EstatePrice> result = sut.getFavoriteEstatePrice(userId);

            // Then
            then(result).hasSize(2)
                    .containsExactlyInAnyOrder(stub, stub2);
        }
    }

    @Nested
    @DisplayName("소유 아파트 조회")
    class OwnershipTest {

        @Test
        @DisplayName("[happy] 나의 소유 아파트 가격을 조회합니다.")
        void getMyEstatePrices_success() {
            // Given
            Long userId = 1L;
            String kaptCode1 = "KaptCode1";
            String kaptCode2 = "KaptCode2";

            given(userPort.checkExistingById(userId)).willReturn(true);

            EstateOwnership 소유1 = EstateOwnerShipFixtures.stub(userId, kaptCode1, LocalDateTime.now());
            EstateOwnership 소유2 = EstateOwnerShipFixtures.stub(userId, kaptCode2, LocalDateTime.now());

            given(estateOwnerShipPort.loadMyOwnerships(userId))
                    .willReturn(List.of(소유1, 소유2));

            EstatePrice stub = EstatePriceFixtures.stub(kaptCode1);
            EstatePrice stub2 = EstatePriceFixtures.stub(kaptCode2);

            given(estatePricePort.loadEstatePricesByCodes(List.of(kaptCode1, kaptCode2)))
                    .willReturn(List.of(stub, stub2));

            // When
            List<EstatePrice> result = sut.getMyEstatePrices(userId);

            // Then
            then(result).hasSize(2)
                    .containsExactlyInAnyOrder(stub, stub2);
        }
    }

    @Nested
    @DisplayName("예외 처리")
    class ExceptionTest {

        @Test
        @DisplayName("[error] 존재하지 않는 유저일 경우 예외를 던집니다.")
        void throwException_whenUserNotFound() {
            // Given
            Long userId = 1L;
            given(userPort.checkExistingById(userId))
                    .willReturn(false);

            // When / Then
            org.junit.jupiter.api.Assertions.assertThrows(
                    NoSuchElementException.class,
                    () -> sut.getFavoriteRegion(userId)
            );
        }
    }

}

package com.zipte.platform.server.application.service;

import com.zipte.platform.server.application.out.region.RegionPort;
import com.zipte.platform.server.domain.region.Region;
import com.zipte.platform.server.domain.region.RegionFixtures;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class RegionServiceTest {

    @Mock
    private RegionPort regionPort;

    @InjectMocks
    private RegionService sut;

    @Test
    @DisplayName("[edge]존재하지 않는 코드일 경우, 예외처리 발생")
    public void checkExistence() {

        // Given
        String regionCode = "A012345678";
        given(regionPort.loadRegion(any())).willReturn(Optional.empty());

        // When & Then
        Assert.assertThrows(NoSuchElementException.class,
                () -> sut.loadRegion(regionCode));
    }

    @Test
    @DisplayName("[happy] 성남시의 하위목록으로 분당구가 발견된다.")
    void loadChildRegionsByCode_test() {
        // Given
        Region 분당구 = RegionFixtures.분당구();
        String 성남시코드 = "4113000000";  // 성남시의 법정동 코드

        // 서비스 계층으로 분기
        given(regionPort.loadChildRegionsByPrefix(eq("4113"), eq("00000")))
                .willReturn(List.of(분당구));

        // When
        List<Region> regions = sut.loadChildRegionsByCode(성남시코드);

        // Then
        assertThat(regions).hasSize(1);  // 하위 지역이 하나일 경우
        assertThat(regions.get(0).getAddress()).contains("분당구");  // 이름 검증
        assertThat(regions.get(0).getCode()).startsWith("41135");  // 코드 시작 부분 검증
    }

    @Test
    @DisplayName("[error] 잘못된 코드 형식이 입력되었을 때 IllegalArgumentException 발생")
    void loadRegionsByCode_invalidCode_test() {
        // Given
        String 잘못된코드 = "411300";  // 길이가 10이 아닌 코드

        // When & Then
        Assert.assertThrows(IllegalArgumentException.class,
                () -> sut.loadRegion(잘못된코드));
    }


    @Test
    @DisplayName("[error] 잘못된 코드 형식이 입력되었을 때 IllegalArgumentException 발생")
    void loadChildRegionsByCode_invalidCode_test() {
        // Given
        String 잘못된코드 = "411300";  // 길이가 10이 아닌 코드

        // When & Then
        Assert.assertThrows(IllegalArgumentException.class,
                () -> sut.loadChildRegionsByCode(잘못된코드));
    }

    @Test
    @DisplayName("[happy] 서울특별시 종로구 하위 지역 조회")
    void loadChildRegionsByCode_seoul_test() {
        // Given
        Region 종로구 = RegionFixtures.종로구();
        Region 종로구_청운동 = RegionFixtures.종로구_청운동();

        given(regionPort.loadChildRegionsByPrefix(eq("11110"), eq("00")))
                .willReturn(List.of(종로구_청운동));

        // When
        List<Region> regions = sut.loadChildRegionsByCode(종로구.getCode());

        // Then
        assertThat(regions).hasSize(1);
        assertThat(regions.get(0).getAddress()).contains("청운동");
    }



}

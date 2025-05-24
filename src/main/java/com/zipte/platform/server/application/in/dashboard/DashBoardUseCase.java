package com.zipte.platform.server.application.in.dashboard;

import com.zipte.platform.server.domain.estate.Estate;
import com.zipte.platform.server.domain.estate.EstatePrice;
import com.zipte.platform.server.domain.region.Region;
import com.zipte.platform.server.domain.region.RegionPrice;

import java.util.List;

public interface DashBoardUseCase {

    /*
        매매 / 관심 지역	관심 지역 - 실거래가 동향	관심 지역으로 설정한 지역의 가격 변화에 대한 알림을 받는다.
        매매 / 관심 아파트	관심 아파트 - 시세 변화	관심 아파트의 가격 변화에 대한 알림을 받는다.
        전체 / 맞춤형 아파트 제공	맞춤형 추천 매물 현황	추천시스템을 통해 관심 아파트로 설정한 유사 아파트를 제공한다.
        나의 아파트 가격 변화 / 나의 아파트 가격 변화 조회	/ 개인정보에 등록한 나의 아파트의 가격 변화를 그래프로 확인한다.
     */


    /// 관심 지역 조회하기
    List<Region> getFavoriteRegion(Long userId);

    /// 관심 지역 가격 조회하기
    List<RegionPrice> getFavoriteRegionPrices(Long userId);


    /// 관심 아파트 목록 조회하기
    List<Estate> getFavoriteEstates(Long userId);


    /// 관심 아파트 가격 목록 조회하기
    List<EstatePrice> getFavoriteEstatePrice(Long userId);


    /// AI 추천 목록 조회하기


    /// 주거지 인증을 한 나의 아파트 조회하기
    List<EstatePrice> getMyEstatePrices(Long userId);



}

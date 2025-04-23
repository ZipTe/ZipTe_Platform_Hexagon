package com.zipte.platform.server.application.out.estateOwnership;

import com.zipte.platform.server.domain.estateOwnership.EstateOwnership;

public interface EstateOwnerShipPort {

    /// 저장하기
    EstateOwnership saveOwnership(EstateOwnership estateOwnership);

    /// 조회하기
    // 해당 유저가 실거주자인지 여부 체크
    boolean loadOwnershipByUser(Long userId, String kaptCode);

    /// 삭제하기
    void deleteOwnership(Long userId, String kaptCode);


}

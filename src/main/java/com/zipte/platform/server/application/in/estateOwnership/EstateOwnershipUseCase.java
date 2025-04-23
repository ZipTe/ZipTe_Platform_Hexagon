package com.zipte.platform.server.application.in.estateOwnership;

import com.zipte.platform.server.adapter.in.web.dto.request.EstateOwnershipRequest;
import com.zipte.platform.server.domain.estateOwnership.EstateOwnership;

public interface EstateOwnershipUseCase {

    /*
        초반에는 위치 기반으로 진행
        추후, 이미지의 OCR을 통해 진행한다.
     */

    // 아파트 소유에 대한 인증
    EstateOwnership createOwnership(EstateOwnershipRequest request);

    // 아파트 소유여부 해제하기
    void deleteOwnership(Long userId, String kaptCode);


}

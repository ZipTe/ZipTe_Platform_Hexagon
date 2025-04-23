package com.zipte.platform.server.application.out.estate;

import com.zipte.platform.server.domain.estate.Estate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LoadEstatePort {

    List<Estate> loadEstatesNearBy( double longitude, double latitude, double radiusInKm);

    Optional<Estate> loadEstateByCode(String kaptCode);

    Optional<Estate> loadEstateByName(String kaptName);

    Page<Estate> loadEstatesByRegion(String region, Pageable pageable);

    boolean checkExistingByCode(String kaptCode);

}

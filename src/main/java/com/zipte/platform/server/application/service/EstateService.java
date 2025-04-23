package com.zipte.platform.server.application.service;

import com.zipte.platform.server.adapter.in.web.dto.response.EstateListResponse;
import com.zipte.platform.server.application.in.estate.GetEstateUseCase;
import com.zipte.platform.server.application.out.estate.LoadEstatePort;
import com.zipte.platform.server.application.out.property.LoadPropertyPort;
import com.zipte.platform.server.domain.estate.Estate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstateService implements GetEstateUseCase {

    private final LoadEstatePort loadPort;

    private final LoadPropertyPort loadPropertyPort;

    @Override
    public List<Estate> loadEstatesNearBy(double latitude, double longitude, double radiusInKm) {
        double radiusInRadians = radiusInKm / 6378.1;  // 반경 km -> radians 변환
        return loadPort.loadEstatesNearBy(latitude, longitude, radiusInRadians);
    }

    @Override
    public List<EstateListResponse> loadEstatesNearByProperty(double latitude, double longitude, double radiusInKm) {

        double radiusInRadians = radiusInKm / 6378.1;  // 반경 km -> radians 변환
        List<Estate> estates = loadPort.loadEstatesNearBy(latitude, longitude, radiusInRadians);

        List<EstateListResponse> result = new ArrayList<>();

        estates.forEach(estate -> {
            int count = loadPropertyPort.countPropertiesInEstate(estate.getKaptCode());
            if (count > 0) {
                result.add(EstateListResponse.from(estate, count));
            }
        });

        return result;
    }


    @Override
    public Optional<Estate> loadEstateByCode(String kaptCode) {
        return loadPort.loadEstateByCode(kaptCode);
    }

    @Override
    public Optional<Estate> loadEstateByName(String kaptName) {
        return loadPort.loadEstateByName(kaptName);
    }

    @Override
    public Page<Estate> loadEstatesByRegion(String region, Pageable pageable) {
        return loadPort.loadEstatesByRegion(region, pageable);
    }

}

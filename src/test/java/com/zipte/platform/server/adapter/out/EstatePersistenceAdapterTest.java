package com.zipte.platform.server.adapter.out;

import com.zipte.platform.server.adapter.out.mongo.estate.EstateDocument;
import com.zipte.platform.server.adapter.out.mongo.estate.EstateMongoRepository;
import com.zipte.platform.server.domain.estate.Estate;
import com.zipte.platform.server.domain.estate.EstateFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EstatePersistenceAdapterTest {

    @Autowired
    private EstateMongoRepository repository;

    @Autowired
    private EstatePersistenceAdapter sut;

    @BeforeEach
    void setUp() {

        /// 테스트 값을 넣어둔다.
        Estate stub = EstateFixtures.stub();

        /// 기존 값 삭제
        repository.deleteByKaptCode(stub.getKaptCode());

        /// 객체 생성
        EstateDocument document = EstateDocument.from(stub);

        /// DB에 해당 값을 저장한다.
        repository.save(document);
    }

    @Nested
    @DisplayName("조회 테스트")
    class Load {

        @Test
        @DisplayName("[happy] 정상 조회")
        void load_happy() {

            // Given
            Estate stub = EstateFixtures.stub();
            String kaptCode = stub.getKaptCode();

            // When
            Optional<Estate> estate = sut.loadEstateByCode(kaptCode);
            Estate result = estate.get();

            // Then
            Assertions.assertThat(repository.findByKaptCode(kaptCode))
                    .isNotEmpty();

            Assertions.assertThat(result.getKaptCode()).isEqualTo(stub.getKaptCode());
        }

        @Test
        @DisplayName("[happy] 지역 기반 페이징 조회 성공")
        void loadEstatesByRegionWithPaging_happy() {
            // Given
            Estate stub = EstateFixtures.stub();
            Pageable pageable = PageRequest.of(0, 10);

            // When
            Page<Estate> result = sut.loadEstatesByRegion(stub.getKaptAddr(), pageable);

            // Then
            Assertions.assertThat(result.getContent()).isNotEmpty();
            Assertions.assertThat(result.getContent().get(0).getKaptAddr())
                    .contains(stub.getKaptAddr());
        }

        @Test
        @DisplayName("[happy] 지역 기반 전체 조회 성공")
        void loadEstatesByRegionWithoutPaging_happy() {
            // Given
            Estate stub = EstateFixtures.stub();

            // When
            List<Estate> result = sut.loadEstatesByRegion(stub.getKaptAddr());

            // Then
            Assertions.assertThat(result).isNotEmpty();
            Assertions.assertThat(result.get(0).getKaptAddr())
                    .contains(stub.getKaptAddr());
        }

        @Test
        @DisplayName("[happy] 이름 기반 조회 성공")
        void loadByName_happy() {
            // Given
            Estate stub = EstateFixtures.stub();

            // When
            Optional<Estate> result = sut.loadEstateByName(stub.getKaptName());

            // Then
            Assertions.assertThat(result).isPresent();
            Assertions.assertThat(result.get().getKaptName())
                    .isEqualTo(stub.getKaptName());
        }


        @Test
        @DisplayName("[happy] 지정 반경 내 근처 아파트 조회 성공")
        void loadEstatesNearBy_happy() {
            // Given
            Estate stub = EstateFixtures.stub();
            double lat = stub.getLocation().getLatitude();
            double lon = stub.getLocation().getLongitude();
            double radius = 1.0; // 1km 반경

            // When
            List<Estate> result = sut.loadEstatesNearBy(lon, lat, radius);

            // Then
            Assertions.assertThat(result).isNotEmpty();
            Assertions.assertThat(result.get(0).getKaptCode())
                    .isEqualTo(stub.getKaptCode());
        }
    }

    @Nested
    @DisplayName("체크 테스트")
    class Check {

        @Test
        @DisplayName("[happy] 코드에 해당하는 값이 있는 경우 true 반환")
        void exists_happy() {
            // Given
            Estate stub = EstateFixtures.stub();
            String kaptCode = stub.getKaptCode();

            // When
            boolean exists = sut.checkExistingByCode(kaptCode);

            // Then
            Assertions.assertThat(exists).isTrue();
        }

        @Test
        @DisplayName("[happy] 코드에 해당하는 값이 없는 경우 false 반환")
        void not_exists_happy() {
            // Given
            String nonExistCode = "NON_EXIST_KAPT_CODE";

            // When
            boolean exists = sut.checkExistingByCode(nonExistCode);

            // Then
            Assertions.assertThat(exists).isFalse();
        }

        @Test
        @DisplayName("[edge] 존재하지 않는 KAPT 코드로 false 반환")
        void checkExistingByCode_false() {
            // Given
            String fakeCode = "FAKE-CODE-0000";

            // When
            boolean exists = sut.checkExistingByCode(fakeCode);

            // Then
            Assertions.assertThat(exists).isFalse();
        }


    }


}

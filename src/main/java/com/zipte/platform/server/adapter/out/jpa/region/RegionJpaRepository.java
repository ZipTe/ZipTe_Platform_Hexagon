package com.zipte.platform.server.adapter.out.jpa.region;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface RegionJpaRepository extends JpaRepository<RegionJpaEntity, String> {

    RegionJpaEntity findByCode(String code);

    @Query("SELECT r from RegionJpaEntity r where r.parentCode =: parentCode")
    List<RegionJpaEntity> findByParentCode(String parentCode);
}

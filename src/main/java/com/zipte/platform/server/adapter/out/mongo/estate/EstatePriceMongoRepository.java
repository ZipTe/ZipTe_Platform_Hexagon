package com.zipte.platform.server.adapter.out.mongo.estate;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EstatePriceMongoRepository extends MongoRepository<EstatePriceDocument, String> {

    List<EstatePriceDocument> findAllByKaptCode(String kaptCode);

    List<EstatePriceDocument> findALlByKaptCodeAndExclusiveArea(String kaptCode, double exclusiveArea);

}

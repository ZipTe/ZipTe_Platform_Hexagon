package com.zipte.platform.server.adapter.out.mongo.estate;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EstatePriceMongoRepository extends MongoRepository<EstatePriceDocument, String> {

    List<EstatePriceDocument> findAllByKaptCode(String kaptCode);

    List<EstatePriceDocument> findAllByKaptCodeAndExclusiveArea(String kaptCode, double exclusiveArea);

    Optional<EstatePriceDocument> findFirstByKaptCodeOrderByTransactionDateDesc(String kaptCode);

}

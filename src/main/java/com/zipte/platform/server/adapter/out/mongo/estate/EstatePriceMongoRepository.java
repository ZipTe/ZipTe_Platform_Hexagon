package com.zipte.platform.server.adapter.out.mongo.estate;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EstatePriceMongoRepository extends MongoRepository<EstatePriceDocument, String> {
}

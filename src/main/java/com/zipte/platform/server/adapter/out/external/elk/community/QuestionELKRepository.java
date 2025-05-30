package com.zipte.platform.server.adapter.out.external.elk.community;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface QuestionELKRepository extends ElasticsearchRepository<QuestionELKDocument, Long> {

    Page<QuestionELKDocument> findByTitleAndKaptCode(String title, String kaptCode, Pageable pageable);

    List<QuestionELKDocument> findByTitle(String title);

    List<QuestionELKDocument> findByTitleAndKaptCode(String title, String kaptCode);


}

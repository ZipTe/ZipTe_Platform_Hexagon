package com.zipte.platform.server.domain.qa;

import com.zipte.platform.server.adapter.in.web.dto.response.QuestionAnswerListResponse;
import com.zipte.platform.server.domain.community.Answer;
import com.zipte.platform.server.domain.community.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.zipte.platform.server.domain.qa.AnswerJpaFixtures.stubAnswer;

public class QuestionAnswerFixtures {

    // 단일 QuestionAnswerListResponse 생성
    public static QuestionAnswerListResponse stubResponse(boolean withAnswer) {

        Question question = QuestionFixtures.stub();

        Optional<Answer> answer = withAnswer ? Optional.of(AnswerFixtures.stub()) : Optional.empty();
        return QuestionAnswerListResponse.from(question, answer);
    }

    public static Page<QuestionAnswerListResponse> stubPage(int page, int size) {
        List<QuestionAnswerListResponse> questions = List.of(
                stubResponse(true),
                stubResponse(false),
                stubResponse(true)
        );

        return new PageImpl<>(questions, PageRequest.of(page, size), questions.size());  // 총 100개 가정
    }

}

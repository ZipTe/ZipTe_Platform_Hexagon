package com.zipte.platform.server.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.security.annotation.WithMockCustomUser;
import com.zipte.platform.server.adapter.in.web.dto.request.AnswerRequest;
import com.zipte.platform.server.adapter.in.web.dto.request.QuestionRequest;
import com.zipte.platform.server.adapter.in.web.dto.response.QuestionAnswerListResponse;
import com.zipte.platform.server.application.in.community.AnswerUseCase;
import com.zipte.platform.server.application.in.community.QuestionUseCase;
import com.zipte.platform.server.domain.qa.AnswerRequestFixtures;
import com.zipte.platform.server.domain.qa.QuestionAnswerFixtures;
import com.zipte.platform.server.domain.qa.QuestionRequestFixtures;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@ActiveProfiles("test")
class QaApiTest {

    private static final Logger log = LoggerFactory.getLogger(QaApiTest.class);
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QuestionUseCase questionService;

    @MockitoBean
    private AnswerUseCase answerService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Nested
    @DisplayName("질문 생성")
    class CreateQuestion {

        @Test
        @WithMockCustomUser
        @DisplayName("[happy] 정상적으로 질문을 생성합니다.")
        void create() throws Exception {

            // Given
            Long userId = 1L;
            QuestionRequest request = QuestionRequestFixtures.stub(userId);

            ApiResponse<String> response = ApiResponse.ok("성공적으로 질문이 저장되었습니다.");

            // When
            ResultActions resultActions = mockMvc.perform(post("/api/v1/question")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            // Then
            MvcResult mvcResult = resultActions
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();
            String expectedJson = objectMapper.writeValueAsString(response);

            org.junit.jupiter.api.Assertions.assertEquals(expectedJson, responseBody);
        }


    }

    @Nested
    @DisplayName("질문 조회")
    class GetQuestion {

        @Test
        @DisplayName("[happy] 아파트 코드에 대한 질문 정상 조회")
        void get() throws Exception {
            // Given
            String kaptCode = "test";
            int page = 1;
            int size = 10;

            Page<QuestionAnswerListResponse> responses = QuestionAnswerFixtures.stubPage(page, size);

            given(questionService.loadQuestions(any(), any()))
                    .willReturn(responses);

            // When
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/question/" + kaptCode)
                    .param("page", String.valueOf(page))
                    .param("size", String.valueOf(size)));

            // Then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andReturn();
        }
    }

    @Nested
    @DisplayName("답변 생성")
    class CreateAnswer {

        @Test
        @WithMockCustomUser
        @DisplayName("[happy] 답변 정상 생성")
        void create() throws Exception {

            // Given
            Long userId = 1L;
            Long questionId = 100L;
            AnswerRequest request = AnswerRequestFixtures.stub(userId, questionId);
            String jsonRequest = objectMapper.writeValueAsString(request);

            ApiResponse<String> ok = ApiResponse.ok("성공적으로 답변이 저장되었습니다.");
            String expectedJson = objectMapper.writeValueAsString(ok);

            // When
            ResultActions resultActions = mockMvc.perform(post("/api/v1/answer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest));

            // Then
            MvcResult mvcResult = resultActions
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();


            org.junit.jupiter.api.Assertions.assertEquals(expectedJson, responseBody);

        }
    }


    @Nested
    @DisplayName("질문 생성 유효성 실패")
    class CreateValidationFailTests {

        private static final Long USER_ID = 1L;

        static Stream<QuestionRequest> invalidRequests() {
            return Stream.of(
                    QuestionRequestFixtures.kaptCodeNull(USER_ID),
                    QuestionRequestFixtures.titleNull(USER_ID),
                    QuestionRequestFixtures.contentNull(USER_ID),
                    QuestionRequestFixtures.contentTooLong(USER_ID)
            );
        }

        @ParameterizedTest(name = "[error] 질문 생성 유효성 실패 요청 {index}")
        @MethodSource("invalidRequests")
        @WithMockCustomUser
        void create_fail_invalid_parameters(QuestionRequest invalidRequest) throws Exception {
            // When
            ResultActions resultActions = mockMvc.perform(
                    post("/api/v1/question")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidRequest))
            );

            // Then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("답변 생성 유효성 실패")
    class CreateAnswerValidationFailTests {

        private static final Long USER_ID = 1L;
        private static final Long QUESTION_ID = 100L;
        static Stream<AnswerRequest> invalidRequests() {
            return Stream.of(
                    AnswerRequestFixtures.contentNull(USER_ID, QUESTION_ID),
                    AnswerRequestFixtures.questionIdNull(USER_ID)
            );
        }

        @ParameterizedTest(name = "[error] 답변 생성 유효성 실패 요청 {index}")
        @MethodSource("invalidRequests")
        @WithMockCustomUser
        void create_fail_invalid_parameters(AnswerRequest invalidRequest) throws Exception {
            // When
            ResultActions resultActions = mockMvc.perform(
                    post("/api/v1/answer")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidRequest))
            );

            // Then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }
    }


}

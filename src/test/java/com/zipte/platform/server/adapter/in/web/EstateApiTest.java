package com.zipte.platform.server.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.core.response.pageable.PageRequest;
import com.zipte.platform.core.response.pageable.PageResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.EstateDetailResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.EstateListResponse;
import com.zipte.platform.server.application.in.estate.EstatePriceUseCase;
import com.zipte.platform.server.application.in.estate.GetEstateUseCase;
import com.zipte.platform.server.application.in.external.OpenAiUseCase;
import com.zipte.platform.server.domain.estate.Estate;
import com.zipte.platform.server.domain.estate.EstateFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class EstateApiTest {


    @MockitoBean
    private GetEstateUseCase getService;

    @MockitoBean
    private EstatePriceUseCase priceService;

    @MockitoBean
    private OpenAiUseCase openAiService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Nested
    @DisplayName("아파트 조회")
    class Get {

        @Test
        @DisplayName("[happy] Code를 바탕으로 정상적으로 아파트를 조회한다.")
        void loadByCode_happy() throws Exception {

            // Given
            String code = "test_code";
            Estate stub = EstateFixtures.stub();

            given(getService.loadEstateByCode(code))
                    .willReturn(stub);

            ApiResponse<EstateDetailResponse> response = ApiResponse.ok(EstateDetailResponse.from(stub));

            // When
            ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/api/v1/estate")
                            .param("code", code)
                            .contentType(MediaType.APPLICATION_JSON));

            // Then
            MvcResult mvcResult = resultActions
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();


            String expectResponse = objectMapper.writeValueAsString(response);
            mvcResult.getResponse().setCharacterEncoding("UTF-8");
            String responseBody = mvcResult.getResponse().getContentAsString();

            Assertions.assertThat(response).isNotNull();
            Assertions.assertThat(responseBody).isEqualTo(expectResponse);


        }


        @Test
        @DisplayName("[happy] 이름을 바탕으로 정상적으로 아파트를 조회한다.")
        void loadByName_happy() throws Exception {

            // Given
            String name = "테스트아파트";
            Estate stub = EstateFixtures.stub();  // 고정된 stub 객체
            given(getService.loadEstateByName(name))
                    .willReturn(stub);

            ApiResponse<EstateDetailResponse> response = ApiResponse.created(EstateDetailResponse.from(stub));

            // When
            ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/api/v1/estate")
                            .param("name", name)
                            .contentType(MediaType.APPLICATION_JSON));

            // Then
            MvcResult mvcResult = resultActions
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            String expectedResponse = objectMapper.writeValueAsString(response);
            mvcResult.getResponse().setCharacterEncoding("UTF-8");
            String actualResponse = mvcResult.getResponse().getContentAsString();

            Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
        }

        @Test
        @DisplayName("[happy] 특정 지역의 아파트 리스트를 정상적으로 조회한다.")
        void getEstateByRegion_happy() throws Exception {
            // Given
            String region = "서울시 강남구";
            PageRequest pageRequest = new PageRequest(1, 10);
            Pageable pageable = org.springframework.data.domain.PageRequest.of(1, 10);
            List<Estate> content = List.of(EstateFixtures.stub());

            PageImpl<Estate> page = new PageImpl<>(content, pageable, 1);

            given(getService.loadEstatesByRegion(eq(region), any(Pageable.class)))
                    .willReturn(page);


            List<EstateListResponse> dtos = EstateListResponse.from(content);
            ApiResponse<PageResponse<EstateListResponse>> response = ApiResponse.ok(
                    new PageResponse<>(dtos, pageRequest, page.getTotalElements())
            );

            // When
            ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/api/v1/estate/list")
                            .param("region", region)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // Then
            MvcResult result = resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data.dtoList[0].complexCode").value("TEST-KAPT-CODE-1234"))
                    .andExpect(jsonPath("$.data.pageRequest.page").value(1))
                    .andExpect(jsonPath("$.data.pageNumList[0]").value(1))
                    .andDo(print())
                    .andReturn();
        }

        @Test
        @DisplayName("[happy] 특정 좌표 반경 내 아파트 목록을 정상적으로 조회한다.")
        void getEstateByLocation_happy() throws Exception {
            // Given
            double longitude = 127.0;
            double latitude = 37.5;
            double radius = 1.0;

            List<EstateListResponse> expectedList = EstateListResponse.from(List.of(EstateFixtures.stub()));
            given(getService.loadEstatesNearBy(longitude, latitude, radius)).willReturn(expectedList);

            // When
            ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/api/v1/estate/list/location")
                            .param("longitude", String.valueOf(longitude))
                            .param("latitude", String.valueOf(latitude))
                            .param("radius", String.valueOf(radius))
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // Then
            MvcResult result = resultActions.andExpect(status().isOk()).andDo(print()).andReturn();
            String expected = objectMapper.writeValueAsString(ApiResponse.ok(expectedList));
            String actual = result.getResponse().getContentAsString();

            Assertions.assertThat(actual).isEqualTo(expected);
        }


        @Test
        @DisplayName("[happy] AI를 통한 아파트 요약 정보를 정상적으로 조회한다.")
        void getEstateDetailAI_happy() throws Exception {
            // Given
            String kaptCode = "APT1234";
            String aiSummary = "이 아파트는 좋은 입지와 편리한 교통을 갖추고 있습니다.";

            given(openAiService.getKaptCharacteristic(kaptCode))
                    .willReturn(aiSummary);

            // When
            ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/api/v1/estate/ai/" + kaptCode)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // Then
            MvcResult result = resultActions.andExpect(status().isOk()).andDo(print()).andReturn();
            String expected = objectMapper.writeValueAsString(ApiResponse.ok(aiSummary));
            String actual = result.getResponse().getContentAsString();

            Assertions.assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("[happy] 두 개의 아파트를 비교 조회한다.")
        void getEstateByCompare_happy() throws Exception {
            // Given
            String first = "APT1";
            String second = "APT2";
            List<Estate> estates = List.of(EstateFixtures.stub(), EstateFixtures.stub());

            given(getService.loadEstatesByCompare(List.of(first, second))).willReturn(estates);

            List<EstateDetailResponse> responseList = EstateDetailResponse.from(estates);

            // When
            ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/api/v1/estate/compare")
                            .param("first", first)
                            .param("second", second)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // Then
            MvcResult result = resultActions.andExpect(status().isOk()).andDo(print()).andReturn();
            String expected = objectMapper.writeValueAsString(ApiResponse.ok(responseList));
            String actual = result.getResponse().getContentAsString();

            Assertions.assertThat(actual).isEqualTo(expected);
        }



    }

}

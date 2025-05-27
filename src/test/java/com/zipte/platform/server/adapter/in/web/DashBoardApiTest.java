package com.zipte.platform.server.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.security.annotation.WithMockCustomUser;
import com.zipte.platform.server.adapter.in.web.dto.response.EstateDetailResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.EstatePriceListResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.RegionPriceResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.RegionResponse;
import com.zipte.platform.server.application.service.DashBoardService;
import com.zipte.platform.server.domain.estate.Estate;
import com.zipte.platform.server.domain.estate.EstateFixtures;
import com.zipte.platform.server.domain.estate.EstatePrice;
import com.zipte.platform.server.domain.estate.EstatePriceFixtures;
import com.zipte.platform.server.domain.region.Region;
import com.zipte.platform.server.domain.region.RegionFixtures;
import com.zipte.platform.server.domain.region.RegionPrice;
import com.zipte.platform.server.domain.region.RegionPriceFixtures;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class DashBoardApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DashBoardService dashBoardService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Nested
    @DisplayName("지역 관련")
    class RegionLoad {


        @Test
        @WithMockCustomUser
        @DisplayName("[happy] 지역관련")
        void happy_region() throws Exception {

            // Given
            Long userId = 1L;

            Region region1 = RegionFixtures.분당구();
            Region region2 = RegionFixtures.종로구();
            List<Region> responses = List.of(region1, region2);
            given(dashBoardService.getFavoriteRegion(userId))
                    .willReturn(responses);
            List<RegionResponse> responseList = RegionResponse.from(responses);

            ApiResponse<List<RegionResponse>> apiResponse = ApiResponse.ok(responseList);

            // When
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/dashboard/region")
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            MvcResult mvcResult = resultActions
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();

            assertEquals(responseBody, objectMapper.writeValueAsString(apiResponse));
        }
    }


    @Nested
    @DisplayName("지역 가격 관련")
    class RegionPriceLoad {

        @Test
        @WithMockCustomUser
        @DisplayName("[happy] 관심 지역 가격 조회")
        void getFavoriteRegionPrices_success() throws Exception {
            // Given
            Long userId = 1L;

            String regionCode1 = "region1";
            String regionCode2 = "region2";
            RegionPrice stub1 = RegionPriceFixtures.stub(regionCode1);
            RegionPrice stub2 = RegionPriceFixtures.stub(regionCode2);
            var prices = List.of(stub1, stub2);

            given(dashBoardService.getFavoriteRegionPrices(userId))
                    .willReturn(prices);
            ApiResponse<List<RegionPriceResponse>> expectedResponse = ApiResponse.ok(RegionPriceResponse.from(prices));

            // When
            ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/dashboard/region/price")
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            String responseBody = result.andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            assertEquals(objectMapper.writeValueAsString(expectedResponse), responseBody);
        }
    }

    @Nested
    @DisplayName("관심 아파트 관련")
    class EstateLoad {

        @Test
        @WithMockCustomUser
        @DisplayName("[happy] 관심 아파트 조회")
        void getFavoriteEstates_success() throws Exception {
            // Given
            Long userId = 1L;
            Estate 테스트1 = EstateFixtures.stub("테스트1");
            Estate 테스트2 = EstateFixtures.stub("테스트2");


            List<Estate> estates = List.of(테스트1, 테스트2);
            given(dashBoardService.getFavoriteEstates(userId))
                    .willReturn(estates);

            ApiResponse<List<EstateDetailResponse>> expectedResponse = ApiResponse.ok(EstateDetailResponse.from(estates));

            // When
            ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/dashboard/estate")
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            String responseBody = result.andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            assertEquals(objectMapper.writeValueAsString(expectedResponse), responseBody);
        }

        @Test
        @WithMockCustomUser
        @DisplayName("[happy] 관심 아파트 가격 조회")
        void getFavoriteEstatePrices_success() throws Exception {
            // Given
            Long userId = 1L;
            EstatePrice stub1 = EstatePriceFixtures.stub("테스트1");
            EstatePrice stub2 = EstatePriceFixtures.stub("테스트2");
            List<EstatePrice> prices = List.of(stub1, stub2);

            given(dashBoardService.getFavoriteEstatePrice(userId))
                    .willReturn(prices);

            ApiResponse<List<EstatePriceListResponse>> expectedResponse = ApiResponse.ok(EstatePriceListResponse.from(prices));

            // When
            ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/dashboard/estate/price")
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            String responseBody = result.andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            assertEquals(objectMapper.writeValueAsString(expectedResponse), responseBody);
        }
    }

    @Nested
    @DisplayName("내 아파트 관련")
    class MyHome {

        @Test
        @WithMockCustomUser
        @DisplayName("[happy] 내 보유 아파트 가격 조회")
        void getMyEstatePrices_success() throws Exception {
            // Given
            Long userId = 1L;
            EstatePrice stub1 = EstatePriceFixtures.stub("테스트1");

            List<EstatePrice> prices = List.of(stub1);
            given(dashBoardService.getMyEstatePrices(userId))
                    .willReturn(prices);
            ApiResponse<List<EstatePriceListResponse>> expectedResponse = ApiResponse.ok(EstatePriceListResponse.from(prices));

            // When
            ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/dashboard/estate/myhome")
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            String responseBody = result.andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            assertEquals(objectMapper.writeValueAsString(expectedResponse), responseBody);
        }
    }
}

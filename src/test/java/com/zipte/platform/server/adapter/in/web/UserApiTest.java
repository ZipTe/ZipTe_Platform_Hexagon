package com.zipte.platform.server.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.security.annotation.WithMockCustomUser;
import com.zipte.platform.server.adapter.in.web.dto.response.UserMyInfoResponse;
import com.zipte.platform.server.application.in.user.GetUserUseCase;
import com.zipte.platform.server.application.in.user.UpdateUserUseCase;
import com.zipte.platform.server.domain.user.UserFixtures;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class UserApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetUserUseCase getService;

    @MockitoBean
    private UpdateUserUseCase updateService;

    private ObjectMapper objectMapper;


    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Nested
    @DisplayName("유저 조회")
    class LoadUser {

        @Test
        @WithMockCustomUser
        @DisplayName("[happy] 마이 페이지 조회")
        void loadUser_happy() throws Exception {

            // given
            Long userId = 1L;
            UserMyInfoResponse response = UserMyInfoResponse.from(UserFixtures.stub(userId));
            ApiResponse<UserMyInfoResponse> ok = ApiResponse.ok(response);

            given(getService.getMyInfo(userId)).willReturn(UserFixtures.stub(userId));

            // when & then
            ResultActions resultActions = mockMvc.perform(get("/api/v1/users/mypage")
                    .contentType(MediaType.APPLICATION_JSON));

            MvcResult mvcResult = resultActions
                    .andExpect(status().isOk())
                    .andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();
            String expectedJson = objectMapper.writeValueAsString(ok);

            Assertions.assertEquals(expectedJson, responseBody);
        }


    }


}

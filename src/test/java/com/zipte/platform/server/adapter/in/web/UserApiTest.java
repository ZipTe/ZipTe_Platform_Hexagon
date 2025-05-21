package com.zipte.platform.server.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.adapter.in.web.dto.response.UserMyInfoResponse;
import com.zipte.platform.server.application.in.user.GetUserUseCase;
import com.zipte.platform.server.application.in.user.UpdateUserUseCase;
import com.zipte.platform.server.domain.user.User;
import com.zipte.platform.server.domain.user.UserFixtures;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserApiTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private GetUserUseCase getService;

    @Mock
    private UpdateUserUseCase updateService;

    private ObjectMapper objectMapper;

    @InjectMocks
    private UserApi sut;

    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        sut = new UserApi(getService, updateService);
        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
        
        ///  setupSecurityContext
        User user = UserFixtures.stub(1L); // userId = 1L
        PrincipalDetails principalDetails = PrincipalDetails.of(user);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    @Nested
    @DisplayName("유저 조회")
    class LoadUser {

        @Test
        @DisplayName("[happy] 마이 페이지 조회")
        void loadUser_happy() throws Exception {

            // given
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
            Long userId = principal.getId();
            UserMyInfoResponse response = UserMyInfoResponse.from(UserFixtures.stub(userId));

            // when
            when(getService.getMyInfo(userId))
                    .thenReturn(UserFixtures.stub(userId));

            // Then
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/users/mypage") // 실제 API 주소 입력
                    .contentType(MediaType.APPLICATION_JSON));

            MvcResult mvcResult = resultActions
                    .andExpect(status().isOk())
                    .andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();
            String expectedJson = objectMapper.writeValueAsString(response);

            Assertions.assertEquals(responseBody, expectedJson);

        }



    }


}

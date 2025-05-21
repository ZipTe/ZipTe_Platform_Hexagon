package com.zipte.platform.server.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zipte.platform.security.jwt.service.JwtTokenUseCase;
import com.zipte.platform.server.adapter.in.web.dto.request.UserRegisterRequest;
import com.zipte.platform.server.application.in.auth.AuthUserUseCase;
import com.zipte.platform.server.domain.user.UserFixtures;
import com.zipte.platform.server.domain.user.UserRegisterRequestFixtures;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class AuthApiTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private AuthUserUseCase authService;

    @Mock
    private JwtTokenUseCase tokenService;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    private ObjectMapper objectMapper;

    @InjectMocks
    private AuthApi sut;


    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        sut = new AuthApi(authService, tokenService, redisTemplate, objectMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }

    @Nested
    @DisplayName(" 유저 가입 테스트")
    class AuthSave {

        @Test
        @DisplayName("[happy] 회원 가입 테스트")
        public void saveTest() throws Exception
        {
            // given
            UserRegisterRequest request = UserRegisterRequestFixtures.stub();
            String response = "{\"success\":true,\"data\":\"가입이 완료되엇습니다.\",\"error\":null}";


            // when
            when(authService.registerUser(request))
                    .thenReturn(UserFixtures.stub());

            // Then
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/v1/oauth2") // 실제 API 주소 입력
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            MvcResult mvcResult = resultActions
                    .andExpect(status().isOk())
                    .andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();

            Assertions.assertEquals(response, responseBody);

        }

        @Test
        @DisplayName("[edge] 회원 가입 테스트_검증 조건 예외발생")
        public void saveTest_edge() throws Exception {

            // given
            UserRegisterRequest request = UserRegisterRequestFixtures.badRequest();

            // when
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/v1/oauth2") // 실제 API 주소 입력
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            // Then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(response -> Assertions.assertInstanceOf(MethodArgumentNotValidException.class, response.getResolvedException()))
                    .andDo(print())
                    .andReturn();

        }

    }


}

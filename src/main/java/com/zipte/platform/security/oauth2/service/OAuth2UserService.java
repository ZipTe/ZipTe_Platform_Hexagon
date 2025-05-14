package com.zipte.platform.security.oauth2.service;

import com.zipte.platform.security.oauth2.domain.OAuth2UserInfo;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.security.oauth2.domain.google.GoogleUserInfo;
import com.zipte.platform.security.oauth2.domain.kakao.KakaoUserInfo;
import com.zipte.platform.security.oauth2.domain.naver.NaverUserInfo;
import com.zipte.platform.security.oauth2.handler.exception.RedirectToSignupException;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.user.OAuthProvider;
import com.zipte.platform.server.domain.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserPort userPort;
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 유저 정보(attributes) 가져오기
        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();

        // resistrationId 가져오기
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // userNameAttributeName 가져오기
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        log.info(registrationId + ": " + userNameAttributeName);

        // OAuth2를 바탕으로 정보 생성
        OAuth2UserInfo userInfo = null;

        userInfo = getOAuth2UserInfo(registrationId, oAuth2UserAttributes);
        log.info("userInfo: {}", userInfo.getProvider());
        log.info("userInfo: {}", userInfo.getUserName());
        log.info("userInfo: {}", userInfo.getEmail());

        OAuthProvider social = OAuthProvider.valueOf(userInfo.getProvider());
        Optional<User> existUser = userPort.loadUserBySocialAndSocialId(social, userInfo.getProviderId());

        // 존재한다면 로그인
        if (existUser.isPresent()) {
            User user = existUser.get();
            log.info("이미 로그인한 유저입니다.");

            return PrincipalDetails.of(user, oAuth2UserAttributes);

        }
        else{
            // 존재하지 않는다면, 세션에 저장 후
            // 프런트에게 해당 정보를 넘겨주면서 회원가입을 진행한다.
            session.setAttribute("user", userInfo);
            throw new RedirectToSignupException("기존 OAuth2 유저가 존재하지 않기 때문에, 새롭게 회원가입이 필요합니다.");
        }

    }

    /// 내부 함수
    private OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> oAuth2UserAttributes) {
        OAuth2UserInfo userInfo;
        // registrationId에 따라서 유저 정보 dto 생성
        if (registrationId.equals("google")) {
            userInfo = new GoogleUserInfo(oAuth2UserAttributes);
        }

        else if (registrationId.equals("kakao")) {
            userInfo = new KakaoUserInfo(oAuth2UserAttributes);
        }

        else if (registrationId.equals("naver")) {
            userInfo = new NaverUserInfo(oAuth2UserAttributes);
        }

        else {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_REQUEST);
        }
        return userInfo;
    }
}

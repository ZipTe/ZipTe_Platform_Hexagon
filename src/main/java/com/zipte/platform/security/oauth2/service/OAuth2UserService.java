package com.zipte.platform.security.oauth2.service;

import com.zipte.platform.security.oauth2.domain.OAuth2UserInfo;
import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.security.oauth2.handler.exception.RedirectToSignupException;
import com.zipte.platform.security.oauth2.util.OAuth2UserInfoFactory;
import com.zipte.platform.server.application.out.user.UserPort;
import com.zipte.platform.server.domain.user.OAuthProvider;
import com.zipte.platform.server.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
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

        userInfo = OAuth2UserInfoFactory.create(registrationId, oAuth2UserAttributes);
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

        } else {
            // 프런트에게 해당 정보를 넘겨주면서 회원가입을 진행한다.
            throw new RedirectToSignupException(oAuth2UserAttributes, userInfo.getProvider());
        }

    }
}

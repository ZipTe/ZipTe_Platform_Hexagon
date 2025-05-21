package com.zipte.platform.security.annotation;

import com.zipte.platform.security.oauth2.domain.PrincipalDetails;
import com.zipte.platform.server.domain.user.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.*;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser withMockCustomUser) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        //
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add((GrantedAuthority) () -> "ROLE_MEMBER");

        // userId = 1L
        User user = UserFixtures.stub(1L);


        PrincipalDetails principalDetails = PrincipalDetails.of(user);
        System.out.println("principalDetails: " + principalDetails.getUser().getId());
        System.out.println(">>> WithMockCustomUserSecurityContextFactory invoked!");

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(principalDetails, null, grantedAuthorities);


        context.setAuthentication(authentication);

        return context;
    }
}

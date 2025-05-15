package com.zipte.platform.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${zipte.domain}")
    private String domain;

    @Value("${zipte.front}")
    private String host;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(domain, host) // 둘 다 한 번에 등록
                .allowedMethods("*")
                .allowCredentials(true);
    }
}

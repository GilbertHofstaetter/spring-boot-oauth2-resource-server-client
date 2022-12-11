package com.gho.OAuth2ResourceServerClient.conf.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class ApiConfig {

    @Bean
    @RequestScope
    public ApiClient apiClient(OAuth2AuthorizedClientService clientService) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String accessToken = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        if (!StringUtils.isEmpty(accessToken))
            accessToken = accessToken.replace("Bearer ", "");

        if (StringUtils.isEmpty(accessToken) && authentication.getClass()
                .isAssignableFrom(OAuth2AuthenticationToken.class)) {
            OAuth2AuthenticationToken oauthToken =
                    (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId =
                    oauthToken.getAuthorizedClientRegistrationId();

            OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                        clientRegistrationId, oauthToken.getName());
            accessToken = client.getAccessToken().getTokenValue();

        }
        return new ApiClient(accessToken);
    }
}

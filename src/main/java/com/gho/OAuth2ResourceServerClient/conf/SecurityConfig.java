package com.gho.OAuth2ResourceServerClient.conf;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    private final KeycloakLogoutHandler keycloakLogoutHandler;
    @Autowired
    CustomOidcOAuth2UserService customOidcOAuth2UserService;
    @Autowired
    CustomJwtAuthenticationConverter customJwtAuthenticationConverter;

    SecurityConfig(KeycloakLogoutHandler keycloakLogoutHandler) {
        this.keycloakLogoutHandler = keycloakLogoutHandler;
    }

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/employee/list").hasAuthority("ROLE_user")
                .antMatchers("/api/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").hasAnyRole("admin")
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(customJwtAuthenticationConverter());
        http.logout()
                .addLogoutHandler(keycloakLogoutHandler)
                .logoutSuccessUrl("/loggedout")
                .permitAll()
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                //.userService(customOAuth2UserService())
                .oidcUserService(customOidcOAuth2UserService());
        return http.build();
    }

//    private DefaultOAuth2UserService customOAuth2UserService() {
//        return new CustomOAuth2UserService();
//    }

    private OAuth2UserService customOidcOAuth2UserService() {
        return customOidcOAuth2UserService;
    }

    private Converter<Jwt, AbstractAuthenticationToken> customJwtAuthenticationConverter() {
        return customJwtAuthenticationConverter;
    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach(authority -> {
                if (authority instanceof OidcUserAuthority) {
                    OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;

                    OidcIdToken idToken = oidcUserAuthority.getIdToken();
                    OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();

                    try {
                        JWT jwt = JWTParser.parse(idToken.getTokenValue());
                    } catch (Exception e) {

                    }
                    // mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_user"));
                    // Map the claims found in idToken and/or userInfo
                    // to one or more GrantedAuthority's and add it to mappedAuthorities

                } else if (authority instanceof OAuth2UserAuthority) {
                    OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority) authority;
                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
                    // Map the attributes found in userAttributes
                    // to one or more GrantedAuthority's and add it to mappedAuthorities
                }
                mappedAuthorities.add(authority);
            });

            return mappedAuthorities;
        };
    }
}

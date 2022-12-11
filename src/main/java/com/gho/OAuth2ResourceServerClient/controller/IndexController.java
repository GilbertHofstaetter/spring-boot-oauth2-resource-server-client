package com.gho.OAuth2ResourceServerClient.controller;

import com.gho.OAuth2ResourceServerClient.conf.api.ApiClient;
import com.gho.OAuth2ResourceServerClient.obj.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class IndexController {

    @Autowired
    OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Autowired
    ApiClient apiClient;

    @GetMapping("/loggedout")
    public String loggedOut() {
        return "Logged out";
    }

    @GetMapping("/logout")
    @PreAuthorize("hasAnyAuthority('ROLE_user', 'ROLE_admin')")
    public String logout(HttpServletRequest request) throws Exception {
        request.logout();
        return "redirect:/";
    }

    @GetMapping("/userJwt")
    @PreAuthorize("hasAnyAuthority('ROLE_admin') AND hasAuthority('SCOPE_read')") //only in combination with JWT login
    public JwtAuthenticationToken userJwt() {
        String accessToken = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");

        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    @GetMapping("/userOAuth2")
    @PreAuthorize("hasAnyAuthority('ROLE_admin') AND hasAuthority('SCOPE_read')") //only in combination with client login
    public OAuth2AuthenticationToken userOAuth2() {
        //https://spring.io/blog/2018/03/06/using-spring-security-5-to-integrate-with-oauth-2-secured-services-such-as-facebook-and-github
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        OAuth2User user = oauthToken.getPrincipal();

        OAuth2AuthorizedClient client =
                oAuth2AuthorizedClientService.loadAuthorizedClient(
                        oauthToken.getAuthorizedClientRegistrationId(),
                        oauthToken.getName());

        String accessToken = client.getAccessToken().getTokenValue();

        return oauthToken;
    }

    @GetMapping("/testCompanyListApi")
    @PreAuthorize("hasAnyAuthority('ROLE_admin') AND hasAuthority('SCOPE_read')") //only in combination with client login
    public List<Company> testCompanyListApi() {
        try {
            ResponseEntity<Company[]> response = apiClient.getRestTemplate().exchange(RequestEntity.get(new URI("http://localhost:8080/api/company/list")).build(), Company[].class);
            Company[] companies = response.getBody();
            List<Company> list = Arrays.asList(companies);

            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

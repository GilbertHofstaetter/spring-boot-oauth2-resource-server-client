package com.gho.OAuth2ResourceServerClient.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

@RestController
public class IndexController {

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
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    @GetMapping("/userOAuth2")
    @PreAuthorize("hasAnyAuthority('ROLE_admin') AND hasAuthority('SCOPE_read')") //only in combination with client login
    public OAuth2AuthenticationToken userOAuth2() {
        OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        OAuth2User user = authentication.getPrincipal();
        return authentication;
    }

}

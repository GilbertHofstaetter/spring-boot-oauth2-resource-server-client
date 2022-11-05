package com.gho.OAuth2ResourceServerClient.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class CustomOidcOAuth2UserService extends OidcUserService {

    @Autowired
    JwtDecoder jwtDecoderByJwkKeySetUri;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser user = super.loadUser(userRequest);

        Jwt jwt = jwtDecoderByJwkKeySetUri.decode(userRequest.getAccessToken().getTokenValue());
        Collection<? extends GrantedAuthority> extractetRoles = JwtAuthorityExtractor.extractResourceRoles(jwt, "springboot-microservice");
        Set<GrantedAuthority> authoritySet = new HashSet<>();
        extractetRoles.forEach(role -> {
            authoritySet.add(role);
        });
        user.getAuthorities().forEach(role -> {
            authoritySet.add(role);
        });
        user = getUser(userRequest, user.getUserInfo(), authoritySet);

        return user;
    }

    private OidcUser getUser(OidcUserRequest userRequest, OidcUserInfo userInfo, Set<GrantedAuthority> authorities) {
        ClientRegistration.ProviderDetails providerDetails = userRequest.getClientRegistration().getProviderDetails();
        String userNameAttributeName = providerDetails.getUserInfoEndpoint().getUserNameAttributeName();
        return StringUtils.hasText(userNameAttributeName) ? new DefaultOidcUser(authorities, userRequest.getIdToken(), userInfo, userNameAttributeName) : new DefaultOidcUser(authorities, userRequest.getIdToken(), userInfo);
    }
}

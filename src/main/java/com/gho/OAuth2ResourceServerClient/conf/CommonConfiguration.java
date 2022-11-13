package com.gho.OAuth2ResourceServerClient.conf;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class CommonConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Keycloak keycloakAdminClient() {
        //https://keycloak.discourse.group/t/keycloak-admin-client-in-spring-boot/2547/2
        Keycloak keycloak = KeycloakBuilder.builder() //
                .serverUrl("http://keycloak:8091/") //localhost
                .realm("master")
                .grantType(OAuth2Constants.PASSWORD) //
                .clientId("admin-cli")
                .username("admin")
                .password("admin")
                .build();
        return keycloak;
    }
}

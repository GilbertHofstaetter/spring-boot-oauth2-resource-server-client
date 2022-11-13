package com.gho.OAuth2ResourceServerClient.service;

import com.gho.OAuth2ResourceServerClient.conf.KeycloakLogoutHandler;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Service
public class KeycloakService {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakLogoutHandler.class);

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;  //springboot-microservice

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @Autowired
    Keycloak keycloakAdminClient;

    //https://keycloak.discourse.group/t/keycloak-admin-client-in-spring-boot/2547/2
    public void createClient() {
        RealmResource realm = keycloakAdminClient.realm("Demo-Realm");
        ClientsResource clients = realm.clients();
        List<ClientRepresentation> clientRepresentations = clients.findByClientId(clientId);
        if (clientRepresentations.isEmpty()) {
            ClientRepresentation springBootClient = new ClientRepresentation();
            springBootClient.setClientId(clientId);
            springBootClient.setSecret(clientSecret);
            List<String> redirectUris = new ArrayList<>();
            redirectUris.add("http://localhost:8080/*");
            springBootClient.setRedirectUris(redirectUris);
            Response response = clients.create(springBootClient);
//        clientRepresentations.forEach(client -> {
//            String actualClientSecret = client.getSecret();
//            logger.info("Actual client secret: " + actualClientSecret);
//            client.setSecret(clientSecret);
//            Response response = clients.create(client);
//            int status = response.getStatus();
//        });
        }
    }
}

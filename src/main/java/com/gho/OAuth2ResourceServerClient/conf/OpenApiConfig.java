package com.gho.OAuth2ResourceServerClient.conf;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

//@SecurityScheme(name = "security_auth", type = SecuritySchemeType.OAUTH2,
//        flows = @OAuthFlows(authorizationCode = @OAuthFlow(
//                authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}"
//                , tokenUrl = "${springdoc.oAuthFlow.tokenUrl}",scopes = {
//                @OAuthScope(name = "read", description = "read")})))

//@Configuration
//@OpenAPIDefinition(info = @Info(title = "title",
//        description = "description", version = "v1"))
//@SecurityScheme(name = "security_auth", type = SecuritySchemeType.OAUTH2,
//        flows = @OAuthFlows(password = @OAuthFlow(
//                authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}",
//                tokenUrl = "${springdoc.oAuthFlow.tokenUrl}", refreshUrl = "${springdoc.oAuthFlow.tokenUrl}",
//                scopes = {@OAuthScope(name = "read", description = "read")})))
@Configuration
public class OpenApiConfig {

    @Value("${springdoc.oAuthFlow.authorizationUrl}")
    private String authorizationUrl;

    @Value("${springdoc.oAuthFlow.tokenUrl}")
    private String tokenUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        io.swagger.v3.oas.models.security.OAuthFlows flows = new OAuthFlows();
        io.swagger.v3.oas.models.security.OAuthFlow flow = new OAuthFlow();

        flow.setAuthorizationUrl(authorizationUrl);
        flow.setTokenUrl(tokenUrl);

        Scopes scopes = new Scopes();
        scopes.addString("read", "read");
        flow.setScopes(scopes);

        //flows = flows.implicit(flow); //-> has to be activated in keycloak client springboot-microservice
        //flows = flows.authorizationCode(flow); //->
        flows = flows.password(flow); //-> token for entered user
        //flows = flows.clientCredentials(flow); //-> token for serviceaccount user "service-account-springboot-microservice"


        return new OpenAPI()
                .components(new Components().addSecuritySchemes("keycloak",
                        new io.swagger.v3.oas.models.security.SecurityScheme().type(SecurityScheme.Type.OAUTH2).flows(flows)))
                .info(new Info().title("springboot-microservice")
                        .version("v1").description("Demo project for keycloak integration as an authorisation server in a spring boot resource server and client with rbac and scopes authorisation based on spring security."))
                .addSecurityItem(new SecurityRequirement().addList("keycloak",
                        Arrays.asList("read")));
    }
}

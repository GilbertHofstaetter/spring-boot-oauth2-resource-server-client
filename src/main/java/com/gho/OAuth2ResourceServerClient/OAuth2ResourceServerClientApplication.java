package com.ranna.OAuth2ResourceServerClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.client.RestTemplate;

//https://www.djamware.com/post/6225b66ba88c55c95abca0b6/spring-boot-security-postgresql-and-keycloak-rest-api-oauth2
//https://stackoverflow.com/questions/72719400/how-to-configure-oauth2-in-spring-boot-be-spring-boot-fe-keycloak
@SpringBootApplication
public class OAuth2ResourceServerClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2ResourceServerClientApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}

package com.ymmihw.spring.security.auth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.NimbusAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

@Configuration
@PropertySource("application-oauth2.properties")
@Profile("boot")
public class BootSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
           .antMatchers("/oauth_login").permitAll()
           .anyRequest().authenticated()
        .and()
          .oauth2Login()
//            .userInfoEndpoint()
//            .userService(userService)
//            .customUserType(customUserType, clientRegistrationId)
//          .and()
            .authorizationEndpoint()
            .baseUri("/oauth2/authorize-client")
            .authorizationRequestRepository(authorizationRequestRepository())
          .and()
            .tokenEndpoint()
            .accessTokenResponseClient(accessTokenResponseClient())
          .and()
          .loginPage("/oauth_login")
          .defaultSuccessUrl("/loginSuccess")
          .failureUrl("/loginFailure");
  }
  
  @Bean
  public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
    return new HttpSessionOAuth2AuthorizationRequestRepository();
  }

  @Bean
  public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
    return new NimbusAuthorizationCodeTokenResponseClient();
  }
}

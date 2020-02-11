package com.ymmihw.spring.security.oauth2;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import com.ymmihw.spring.security.oauth2.custom.CustomAuthorizationRequestResolver;
import com.ymmihw.spring.security.oauth2.custom.CustomRequestEntityConverter;
import com.ymmihw.spring.security.oauth2.custom.CustomTokenResponseConverter;

@Configuration
@PropertySource("application-oauth2.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private ClientRegistrationRepository clientRegistrationRepository;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/loginFailure", "/")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .oauth2Login()
        .authorizationEndpoint()
        .authorizationRequestResolver(new CustomAuthorizationRequestResolver(
            clientRegistrationRepository, "/oauth2/authorize-client"))
        .baseUri("/oauth2/authorize-client")
        .authorizationRequestRepository(authorizationRequestRepository())
        .and()
        .tokenEndpoint()
        .accessTokenResponseClient(accessTokenResponseClient())
        .and()
        .defaultSuccessUrl("/loginSuccess")
        .failureUrl("/loginFailure");
  }

  @Bean
  public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
    return new HttpSessionOAuth2AuthorizationRequestRepository();
  }

  @Bean
  public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
    DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient =
        new DefaultAuthorizationCodeTokenResponseClient();
    accessTokenResponseClient.setRequestEntityConverter(new CustomRequestEntityConverter());

    OAuth2AccessTokenResponseHttpMessageConverter tokenResponseHttpMessageConverter =
        new OAuth2AccessTokenResponseHttpMessageConverter();
    tokenResponseHttpMessageConverter.setTokenResponseConverter(new CustomTokenResponseConverter());
    RestTemplate restTemplate = new RestTemplate(
        Arrays.asList(new FormHttpMessageConverter(), tokenResponseHttpMessageConverter));
    restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
    accessTokenResponseClient.setRestOperations(restTemplate);
    return accessTokenResponseClient;
  }
}

package com.ymmihw.spring.security.auth2.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception { // @formatter:off
    http.antMatcher("/**")
          .authorizeRequests()
            .antMatchers("/login**").permitAll()
            .anyRequest().authenticated()
        .and().formLogin().disable();
  }// @formatter:on
}
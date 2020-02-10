package com.ymmihw.spring.security.auth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception { // @formatter:off
    http.antMatcher("/**")
          .authorizeRequests()
            .antMatchers("/login**").permitAll()
            .anyRequest().authenticated()
        .and().oauth2Login()
        .and().formLogin().disable();
  }// @formatter:on
}

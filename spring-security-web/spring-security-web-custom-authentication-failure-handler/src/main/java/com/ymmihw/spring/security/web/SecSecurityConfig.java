package com.ymmihw.spring.security.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    auth.inMemoryAuthentication().withUser("1").password(encoder.encode("1")).roles("USER");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().anyRequest().authenticated().and().formLogin()
        .failureHandler(customAuthenticationFailureHandler());
  }

  @Bean
  public AuthenticationFailureHandler customAuthenticationFailureHandler() {
    return new CustomAuthenticationFailureHandler();
  }
}

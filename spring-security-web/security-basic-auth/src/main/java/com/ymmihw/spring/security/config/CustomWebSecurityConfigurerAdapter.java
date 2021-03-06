package com.ymmihw.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser("1").password("1").authorities("ROLE_USER");
  }

  @Autowired
  private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

  @Override
  protected void configure(HttpSecurity http) throws Exception { // @formatter:off
    http.authorizeRequests()
          .antMatchers("/foo").permitAll()
          .anyRequest().authenticated().and()
        .httpBasic().authenticationEntryPoint(authenticationEntryPoint);
  } // @formatter:on
}

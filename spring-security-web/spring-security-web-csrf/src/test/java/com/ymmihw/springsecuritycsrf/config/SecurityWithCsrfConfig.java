package com.ymmihw.springsecuritycsrf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityWithCsrfConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    auth.inMemoryAuthentication().withUser("user1").password(encoder.encode("user1Pass"))
        .authorities("ROLE_USER");
    auth.inMemoryAuthentication().withUser("admin").password(encoder.encode("adminPass"))
        .authorities("ROLE_ADMIN");
  }

  @Override
  public void configure(final WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/resources/**");
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    // @formatter:off
        http.authorizeRequests().antMatchers("/login").permitAll()
            .antMatchers("/auth/admin/*")
            .hasAnyRole("ROLE_ADMIN")
            .anyRequest()
            .authenticated()
            .and()
            .formLogin().and()
            .httpBasic()
            .and()
            .headers()
            .cacheControl()
            .disable();
        // @formatter:on
  }

}

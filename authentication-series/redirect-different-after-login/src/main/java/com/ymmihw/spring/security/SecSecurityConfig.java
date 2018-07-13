package com.ymmihw.spring.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser("1").password("{noop}1").roles("USER");
    auth.inMemoryAuthentication().withUser("2").password("{noop}2").roles("ADMIN");
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception { // @formatter:off
    http.csrf().disable().authorizeRequests()
          .antMatchers("/anonymous*").anonymous()
          .antMatchers("/login*").permitAll()
          .anyRequest().authenticated()
        .and()
        .formLogin()
          .loginPage("/login.html")
          .loginProcessingUrl("/login")
          .successHandler(successHandler())
          .failureUrl("/login.html?error=true");
  } // @formatter:on

  private AuthenticationSuccessHandler successHandler() {
    return new MySimpleUrlAuthenticationSuccessHandler();
  }
}

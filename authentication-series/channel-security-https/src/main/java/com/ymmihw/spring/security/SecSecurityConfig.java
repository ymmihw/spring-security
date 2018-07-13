package com.ymmihw.spring.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

  public SecSecurityConfig() {
    super();
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser("1").password("{noop}1").roles("USER");
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
          .defaultSuccessUrl("/homepage.html", true)
          .failureUrl("/login.html?error=true")
        .and()
          .logout()
          .deleteCookies("JSESSIONID");
    http.requiresChannel().antMatchers("/login*").requiresSecure().anyRequest().requiresInsecure();
    http.sessionManagement().sessionFixation().none();
  } // @formatter:on

}

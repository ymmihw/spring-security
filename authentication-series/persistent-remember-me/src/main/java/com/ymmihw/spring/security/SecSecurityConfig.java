package com.ymmihw.spring.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

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
          .failureUrl("/login.html?error=true")
        .and()
          .logout()
          .deleteCookies("JSESSIONID")
        .and()
          .rememberMe()
          .tokenRepository(tokenRepository())
          .tokenValiditySeconds(86400);
  } // @formatter:on

  @Autowired
  DataSource dataSource;

  private PersistentTokenRepository tokenRepository() {
    JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
    repository.setCreateTableOnStartup(false);
    repository.setDataSource(dataSource);
    return repository;
  }
  
  TokenBasedRememberMeServices a;

}

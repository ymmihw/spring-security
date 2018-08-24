package com.ymmihw.spring.security.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class LssSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    auth.inMemoryAuthentication().withUser("1").password(encoder.encode("1")).roles("USER");
    auth.authenticationProvider(runAsAuthenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {// @formatter:off
    http
      .authorizeRequests()
        .antMatchers("/badUser*","/js/**").permitAll()
        .anyRequest().authenticated()
      .and()
      .formLogin()
        .loginPage("/login").permitAll()
        .loginProcessingUrl("/doLogin")
      .and().logout().permitAll().logoutUrl("/logout")
      .and().csrf().disable();
  } // @formatter:on

  @Bean
  public AuthenticationProvider runAsAuthenticationProvider() {
    final RunAsImplAuthenticationProvider authProvider = new RunAsImplAuthenticationProvider();
    authProvider.setKey("MyRunAsKey");
    return authProvider;
  }
}

package com.ymmihw.spring.security.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser("1").password("1").authorities("ROLE_USER");
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception { // @formatter:off
    http.authorizeRequests()
          .antMatchers("/foo").permitAll()
          .anyRequest().authenticated().and()
        .httpBasic().authenticationEntryPoint(digestAuthenticationEntryPoint()).and()
        .addFilter(digestAuthenticationFilter(digestAuthenticationEntryPoint()));
  } // @formatter:on


  @Override
  @Bean
  public UserDetailsService userDetailsServiceBean() throws Exception {
    return super.userDetailsServiceBean();
  }

  public DigestAuthenticationFilter digestAuthenticationFilter(
      DigestAuthenticationEntryPoint digestAuthenticationEntryPoint) throws Exception {
    DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
    digestAuthenticationFilter.setAuthenticationEntryPoint(digestAuthenticationEntryPoint());
    digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());
    return digestAuthenticationFilter;
  }

  @Bean
  public DigestAuthenticationEntryPoint digestAuthenticationEntryPoint() {
    DigestAuthenticationEntryPoint o = new DigestAuthenticationEntryPoint();
    o.setRealmName("Contacts Realm via Digest Authentication");
    o.setKey("acegi");
    return o;
  }
}

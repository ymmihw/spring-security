package com.ymmihw.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) { // @formatter:off
    return http.authorizeExchange().pathMatchers("/admin")
                 .hasAuthority("ROLE_ADMIN").anyExchange().authenticated()
               .and()
               .formLogin()
               .and()
               .build();
  }// @formatter:on

  @Bean
  public MapReactiveUserDetailsService userDetailsService() {
    UserDetails user = User.withUsername("user").password(passwordEncoder().encode("password"))
        .roles("USER").build();

    UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("password"))
        .roles("ADMIN").build();

    return new MapReactiveUserDetailsService(user, admin);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
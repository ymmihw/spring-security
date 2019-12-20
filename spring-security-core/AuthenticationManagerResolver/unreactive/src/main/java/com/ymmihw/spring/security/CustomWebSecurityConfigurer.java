package com.ymmihw.spring.security;

import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class CustomWebSecurityConfigurer extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) {
    http.addFilterBefore(authenticationFilter(), BasicAuthenticationFilter.class);
  }

  private AuthenticationFilter authenticationFilter() {
    AuthenticationFilter filter = new AuthenticationFilter(resolver(), authenticationConverter());
    filter.setSuccessHandler((request, response, auth) -> {
    });
    return filter;
  }

  private AuthenticationManagerResolver<HttpServletRequest> resolver() {
    return request -> {
      if (request.getPathInfo().startsWith("/employee")) {
        return employeesAuthenticationManager();
      }
      return customersAuthenticationManager();
    };
  }

  private AuthenticationConverter authenticationConverter() {
    return new BasicAuthenticationConverter();
  }

  private AuthenticationManager customersAuthenticationManager() {
    return authentication -> {
      if (isCustomer(authentication)) {
        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
            authentication.getCredentials(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
      }
      throw new UsernameNotFoundException(authentication.getPrincipal().toString());
    };
  }

  private boolean isCustomer(Authentication authentication) {
    return (authentication.getPrincipal().toString().startsWith("customer"));
  }

  private boolean isEmployee(Authentication authentication) {
    return (authentication.getPrincipal().toString().startsWith("employee"));
  }

  private AuthenticationManager employeesAuthenticationManager() {
    return authentication -> {
      if (isEmployee(authentication)) {
        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
            authentication.getCredentials(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
      }
      throw new UsernameNotFoundException(authentication.getPrincipal().toString());
    };
  }

}

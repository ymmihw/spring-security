package com.ymmihw.spring.security.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import com.ymmihw.spring.security.config.entity.CustomUser;
import com.ymmihw.spring.security.config.service.UserRoleService;

@SpringBootTest
@ContextConfiguration
public class TestWithUserDetails {

  @Autowired
  UserRoleService userService;

  @Configuration
  @ComponentScan("com.ymmihw.spring.security.config.*")
  public static class SpringConfig {

  }

  @Test
  @WithUserDetails(value = "john", userDetailsServiceBeanName = "userDetailService")
  public void whenJohn_callLoadUserDetail_thenOK() {
    CustomUser user = userService.loadUserDetail("jane");
    assertEquals("jane", user.getNickName());
  }

  @Test
  @WithUserDetails(value = "jane", userDetailsServiceBeanName = "userDetailService")
  public void givenJane_callSecuredLoadUserDetailWithJane_thenOK() {
    CustomUser user = userService.securedLoadUserDetail("jane");
    assertEquals("jane", user.getNickName());
    assertEquals("jane", user.getUsername());
  }

  @Test
  @WithUserDetails(value = "john", userDetailsServiceBeanName = "userDetailService")
  public void givenJohn_callSecuredLoadUserDetailWithJane_thenAccessDenied() {
    assertThrows(AccessDeniedException.class, () -> userService.securedLoadUserDetail("jane"));
  }

  @Test
  @WithUserDetails(value = "john", userDetailsServiceBeanName = "userDetailService")
  public void givenJohn_callSecuredLoadUserDetailWithJohn_thenAccessDenied() {
    assertThrows(AccessDeniedException.class, () -> userService.securedLoadUserDetail("john"));
  }
}

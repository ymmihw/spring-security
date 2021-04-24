package com.ymmihw.spring.security.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import com.ymmihw.spring.security.config.service.UserRoleService;

@SpringBootTest
@ContextConfiguration
@WithMockUser(username = "john", roles = {"VIEWER"})
public class TestWithMockUserAtClassLevel {

  @Test
  public void givenRoleViewer_whenCallGetUsername_thenReturnUsername() {
    String currentUserName = userService.getUsername();
    assertEquals("john", currentUserName);
  }

  @Autowired
  UserRoleService userService;

  @Configuration
  @ComponentScan("com.ymmihw.spring.security.config.*")
  public static class SpringConfig {

  }
}

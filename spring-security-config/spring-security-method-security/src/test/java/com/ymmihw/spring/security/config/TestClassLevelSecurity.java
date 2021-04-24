package com.ymmihw.spring.security.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import com.ymmihw.spring.security.config.service.SystemService;

@SpringBootTest
@ContextConfiguration
public class TestClassLevelSecurity {

  @Autowired
  SystemService systemService;

  @Configuration
  @ComponentScan("com.ymmihw.spring.security.config.*")
  public static class SpringConfig {

  }

  @Test
  @WithMockUser(username = "john", roles = {"ADMIN"})
  public void givenRoleAdmin_whenCallGetSystemYear_return2017() {
    String systemYear = systemService.getSystemYear();
    assertEquals("2017", systemYear);
  }

  @Test
  @WithMockUser(username = "john", roles = {"VIEWER"})
  public void givenRoleViewer_whenCallGetSystemYear_returnAccessDenied() {
    assertThrows(AccessDeniedException.class, () -> systemService.getSystemYear());
  }

  @Test
  @WithMockUser(username = "john", roles = {"ADMIN"})
  public void givenRoleAdmin_whenCallGetSystemDate_returnDate() {
    String systemYear = systemService.getSystemDate();
    assertEquals("31-12-2017", systemYear);
  }
}

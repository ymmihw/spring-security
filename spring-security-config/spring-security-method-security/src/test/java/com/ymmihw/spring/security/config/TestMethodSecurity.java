package com.ymmihw.spring.security.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import com.ymmihw.spring.security.config.service.UserRoleService;

@SpringBootTest
@ContextConfiguration
public class TestMethodSecurity {

  @Autowired
  UserRoleService userRoleService;

  @Configuration
  @ComponentScan("com.ymmihw.spring.security.config.*")
  public static class SpringConfig {

  }

  @Test
  public void givenNoSecurity_whenCallGetUsername_thenReturnException() {
    assertThrows(AuthenticationCredentialsNotFoundException.class,
        () -> userRoleService.getUsername());
  }

  @Test
  @WithMockUser(username = "john", roles = {"VIEWER"})
  public void givenRoleViewer_whenCallGetUsername_thenReturnUsername() {
    String userName = userRoleService.getUsername();
    assertEquals("john", userName);
  }

  @Test
  @WithMockUser(username = "john", roles = {"EDITOR"})
  public void givenUsernameJohn_whenCallIsValidUsername_thenReturnTrue() {
    boolean isValid = userRoleService.isValidUsername("john");
    assertEquals(true, isValid);
  }

  @Test
  @WithMockUser(username = "john", roles = {"ADMIN"})
  public void givenRoleAdmin_whenCallGetUsername_thenReturnAccessDenied() {
    assertThrows(AccessDeniedException.class, () -> userRoleService.getUsername());
  }

  @Test
  @WithMockUser(username = "john", roles = {"USER"})
  public void givenRoleUser_whenCallGetUsername2_thenReturnAccessDenied() {
    assertThrows(AccessDeniedException.class, () -> userRoleService.getUsername2());
  }

  @Test
  @WithMockUser(username = "john", roles = {"VIEWER", "EDITOR"})
  public void givenRoleViewer_whenCallGetUsername2_thenReturnUsername() {
    String userName = userRoleService.getUsername2();
    assertEquals("john", userName);
  }

  @Test
  @WithMockUser(username = "john", roles = {"VIEWER"})
  public void givenUsernameJerry_whenCallIsValidUsername2_thenReturnFalse() {
    boolean isValid = userRoleService.isValidUsername2("jerry");
    assertEquals(false, isValid);
  }

  @Test
  @WithMockUser(username = "JOHN", authorities = {"SYS_ADMIN"})
  public void givenAuthoritySysAdmin_whenCallGetUsernameInLowerCase_thenReturnUsername() {
    String username = userRoleService.getUsernameLC();
    assertEquals("john", username);
  }

  @Test
  @WithMockUser(username = "john", roles = {"ADMIN", "USER", "VIEWER"})
  public void givenUserJohn_whenCallGetMyRolesWithJohn_thenReturnRoles() {
    String roles = userRoleService.getMyRoles("john");
    assertEquals("ROLE_ADMIN,ROLE_USER,ROLE_VIEWER", roles);
  }

  @Test
  @WithMockUser(username = "john", roles = {"ADMIN", "USER", "VIEWER"})
  public void givenUserJane_whenCallGetMyRolesWithJane_thenAccessDenied() {
    assertThrows(AccessDeniedException.class, () -> userRoleService.getMyRoles("jane"));
  }

  @Test
  @WithMockUser(username = "john", roles = {"ADMIN", "USER", "VIEWER"})
  public void givenUserJohn_whenCallGetMyRoles2WithJohn_thenReturnRoles() {
    String roles = userRoleService.getMyRoles2("john");
    assertEquals("ROLE_ADMIN,ROLE_USER,ROLE_VIEWER", roles);
  }

  @Test
  @WithMockUser(username = "john", roles = {"ADMIN", "USER", "VIEWER"})
  public void givenUserJane_whenCallGetMyRoles2WithJane_thenAccessDenied() {
    assertThrows(AccessDeniedException.class, () -> userRoleService.getMyRoles2("jane"));
  }

  @Test
  @WithAnonymousUser
  public void givenAnomynousUser_whenCallGetUsername_thenAccessDenied() {
    assertThrows(AccessDeniedException.class, () -> userRoleService.getUsername());
  }

  @Test
  @WithMockJohnViewer
  public void givenMockedJohnViewer_whenCallGetUsername_thenReturnUsername() {
    String userName = userRoleService.getUsername();
    assertEquals("john", userName);
  }

  @Test
  @WithMockUser(username = "jane")
  public void givenListContainCurrentUsername_whenJoinUsernames_thenReturnUsernames() {
    List<String> usernames = new ArrayList<>();
    usernames.add("jane");
    usernames.add("john");
    usernames.add("jack");
    String containCurrentUser = userRoleService.joinUsernames(usernames);
    assertEquals("john;jack", containCurrentUser);
  }

  @Test
  @WithMockUser(username = "john")
  public void givenListContainCurrentUsername_whenCallJoinUsernamesAndRoles_thenReturnUsernameAndRoles() {
    List<String> usernames = new ArrayList<>();
    usernames.add("jane");
    usernames.add("john");
    usernames.add("jack");

    List<String> roles = new ArrayList<>();
    roles.add("ROLE_ADMIN");
    roles.add("ROLE_TEST");

    String containCurrentUser = userRoleService.joinUsernamesAndRoles(usernames, roles);
    assertEquals("jane;jack:ROLE_ADMIN;ROLE_TEST", containCurrentUser);
  }

  @Test
  @WithMockUser(username = "john")
  public void givenUserJohn_whenCallGetAllUsernamesExceptCurrent_thenReturnOtherusernames() {
    List<String> others = userRoleService.getAllUsernamesExceptCurrent();
    assertEquals(2, others.size());
    assertTrue(others.contains("jane"));
    assertTrue(others.contains("jack"));
  }

  @Test
  @WithMockUser(username = "john", roles = {"VIEWER"})
  public void givenRoleViewer_whenCallGetUsername4_thenReturnUsername() {
    String userName = userRoleService.getUsername4();
    assertEquals("john", userName);
  }

  @Test
  @WithMockUser(username = "john")
  public void givenDefaultRole_whenCallGetUsername4_thenAccessDenied() {
    assertThrows(AccessDeniedException.class, () -> userRoleService.getUsername4());
  }

}

package com.ymmihw.spring.security.oauth2.sso.auth.server;

import java.security.Principal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  @RequestMapping("/user/me")
  public Principal user(Principal principal) {
    return principal;
  }
}

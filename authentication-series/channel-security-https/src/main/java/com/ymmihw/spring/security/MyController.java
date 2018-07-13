package com.ymmihw.spring.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
  @GetMapping(value = {"/anonymous.html", "/anonymous"})
  public String anonymous() {
    return "anonymous";
  }

  @GetMapping(value = {"/login.html", "/login"})
  public String login() {
    return "login";
  }

  @GetMapping(value = {"/homepage.html", "/homepage"})
  public String homepage() {
    return "homepage";
  }
}

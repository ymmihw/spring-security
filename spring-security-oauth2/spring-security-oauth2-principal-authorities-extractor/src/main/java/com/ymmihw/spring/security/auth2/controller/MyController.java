package com.ymmihw.spring.security.auth2.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {
  @RequestMapping("/")
  public String index() {
    return "oauth2_extractors";
  }
}

package com.ymmihw.spring.security.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

  @GetMapping("foo")
  public String foo() {
    return "foo";
  }

  @GetMapping("bar")
  public String bar() {
    return "bar";
  }
}

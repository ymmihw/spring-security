package com.ymmihw.spring.security.auth2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

  @RequestMapping("/foo")
  @ResponseBody
  public String foo() {
    return "foo";
  }
}

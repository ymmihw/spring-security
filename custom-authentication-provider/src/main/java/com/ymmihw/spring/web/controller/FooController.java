package com.ymmihw.spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/foos")
public class FooController {

  // API

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public Long findOne(@PathVariable("id") final Long id) {
    return id;
  }

}

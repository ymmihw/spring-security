package com.ymmihw.spring.security.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RunAsService {
  @Secured({"ROLE_RUN_AS_REPORTER"})
  public Authentication getCurrentUser() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication;
  }
}

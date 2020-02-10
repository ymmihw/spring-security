package com.ymmihw.spring.security.controller;

import java.util.HashMap;
import java.util.Map;
import com.ymmihw.spring.security.spring.AppConfig.Role;

public class View {

  public static final Map<Role, Class<?>> MAPPING = new HashMap<>();

  static {
    MAPPING.put(Role.ROLE_ADMIN, Admin.class);
    MAPPING.put(Role.ROLE_USER, User.class);
  }

  public static class User {

  }

  public static class Admin extends User {

  }
}

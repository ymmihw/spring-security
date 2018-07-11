package com.ymmihw.spring.security.custom;

public interface UserRepository {
  public User findUser(String username, String domain);
}

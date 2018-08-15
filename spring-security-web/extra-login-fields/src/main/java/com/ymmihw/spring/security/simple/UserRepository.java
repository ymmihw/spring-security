package com.ymmihw.spring.security.simple;

public interface UserRepository {
  public User findUser(String username, String domain);
}

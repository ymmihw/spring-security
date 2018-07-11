package com.ymmihw.spring.security;

public interface UserRepository {
  public User findUser(String username, String domain);
}

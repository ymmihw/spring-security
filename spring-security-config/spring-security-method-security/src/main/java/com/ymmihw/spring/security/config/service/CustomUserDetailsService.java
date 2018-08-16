package com.ymmihw.spring.security.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.ymmihw.spring.security.config.repository.UserRoleRepository;

@Service("userDetailService")
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  UserRoleRepository userRoleRepo;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return userRoleRepo.loadUserByUserName(username);
  }
}

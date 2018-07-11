package com.ymmihw.spring.security.simple;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class SimpleUserDetailsService implements UserDetailsService {

  private UserRepository userRepository;

  public SimpleUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String[] usernameAndDomain = username.split(" ");
    if (usernameAndDomain == null || usernameAndDomain.length != 2) {
      throw new UsernameNotFoundException("Username and domain must be provided");
    }
    User user = userRepository.findUser(usernameAndDomain[0], usernameAndDomain[1]);
    if (user == null) {
      throw new UsernameNotFoundException(
          String.format("Username not found for domain, username=%s, domain=%s",
              usernameAndDomain[0], usernameAndDomain[1]));
    }
    return user;
  }
}

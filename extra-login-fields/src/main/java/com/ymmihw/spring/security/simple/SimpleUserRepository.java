package com.ymmihw.spring.security.simple;

import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public class SimpleUserRepository implements UserRepository {

  @Override
  public User findUser(String username, String domain) {
    if (StringUtils.isAnyBlank(username, domain)) {
      return null;
    } else {
      Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
      User user =
          new User(username, domain, "$2a$10$akHGW/Fg0jQ/FHdKl4ze1ezCeXVmwM.Y7SrI/bpnZvCa7pHaQfxSu",
              true, true, true, true, authorities);
      return user;
    }
  }

}

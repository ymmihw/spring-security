package com.ymmihw.spring.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import com.ymmihw.spring.security.spring.AppConfig;

@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class SpringContextTest {
  @Test
  public void whenSpringContextIsBootstrapped_thenNoExceptions() {}
}

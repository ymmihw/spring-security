package com.ymmihw.springsecuritycsrf;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import javax.servlet.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ymmihw.springsecuritycsrf.dto.Foo;

@SpringBootTest
@WebAppConfiguration
public abstract class CsrfAbstractIntegrationTest {

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private Filter springSecurityFilterChain;

  MockMvc mvc;

  @BeforeEach
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(springSecurityFilterChain).build();
  }

  RequestPostProcessor testUser() {
    return user("user1").password("user1Pass").roles("USER");
  }

  RequestPostProcessor testAdmin() {
    return user("admin").password("adminPass").roles("USER", "ADMIN");
  }

  String createFoo() throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(new Foo(randomAlphabetic(6)));
  }
}

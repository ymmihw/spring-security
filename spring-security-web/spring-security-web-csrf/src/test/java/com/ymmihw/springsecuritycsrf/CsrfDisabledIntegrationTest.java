package com.ymmihw.springsecuritycsrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import com.ymmihw.springsecuritycsrf.config.MvcConfig;
import com.ymmihw.springsecuritycsrf.config.SecurityWithoutCsrfConfig;

@ContextConfiguration(classes = {SecurityWithoutCsrfConfig.class, MvcConfig.class})
public class CsrfDisabledIntegrationTest extends CsrfAbstractIntegrationTest {
  @Test
  public void givenNotAuth_whenAddFoo_thenUnauthorized() throws Exception {
    mvc.perform(post("/auth/foos").contentType(MediaType.APPLICATION_JSON).content(createFoo()))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void givenAuth_whenAddFoo_thenCreated() throws Exception {
    mvc.perform(post("/auth/foos").contentType(MediaType.APPLICATION_JSON).content(createFoo())
        .with(testUser())).andExpect(status().isCreated());
  }
}

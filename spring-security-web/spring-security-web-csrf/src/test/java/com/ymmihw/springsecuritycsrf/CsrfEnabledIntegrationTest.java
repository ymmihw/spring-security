package com.ymmihw.springsecuritycsrf;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import com.ymmihw.springsecuritycsrf.config.MvcConfig;
import com.ymmihw.springsecuritycsrf.config.SecurityWithCsrfConfig;

@ContextConfiguration(classes = {SecurityWithCsrfConfig.class, MvcConfig.class})
public class CsrfEnabledIntegrationTest extends CsrfAbstractIntegrationTest {

  @Test
  public void givenNoCsrf_whenAddFoo_thenForbidden() throws Exception {
    mvc.perform(post("/auth/foos").contentType(MediaType.APPLICATION_JSON).content(createFoo())
        .with(testUser())).andExpect(status().isForbidden());
  }

  @Test
  public void givenCsrf_whenAddFoo_thenCreated() throws Exception {
    mvc.perform(post("/auth/foos").contentType(MediaType.APPLICATION_JSON).content(createFoo())
        .with(testUser()).with(csrf())).andExpect(status().isCreated());
  }
}

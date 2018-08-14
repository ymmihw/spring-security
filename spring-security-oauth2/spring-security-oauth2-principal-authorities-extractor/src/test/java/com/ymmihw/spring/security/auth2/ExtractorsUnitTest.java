package com.ymmihw.spring.security.auth2;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.servlet.Filter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.ymmihw.spring.security.auth2.config.SecurityConfig;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = GithubExtractorsApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {SecurityConfig.class})
@ActiveProfiles("oauth2-extractors-github")
public class ExtractorsUnitTest {

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private Filter springSecurityFilterChain;

  private MockMvc mvc;

  @Before
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(springSecurityFilterChain).build();
  }

  @Test
  public void contextLoads() throws Exception {}

  @Test
  public void givenValidRequestWithoutAuthentication_shouldFailWith302() throws Exception {
    mvc.perform(get("/")).andExpect(status().isFound()).andReturn();
  }

}

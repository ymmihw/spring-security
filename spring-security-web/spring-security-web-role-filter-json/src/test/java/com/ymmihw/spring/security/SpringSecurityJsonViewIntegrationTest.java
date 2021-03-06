package com.ymmihw.spring.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;
import com.ymmihw.spring.security.spring.AppConfig;

@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class SpringSecurityJsonViewIntegrationTest {

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @BeforeEach
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  @WithMockUser(username = "admin", password = "adminPass", roles = "ADMIN")
  public void whenAdminRequests_thenOwnerNameIsPresent() throws Exception {
    mvc.perform(get("/items")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("Item 1"))
        .andExpect(jsonPath("$[0].ownerName").exists());
  }

  @Test
  @WithMockUser(username = "user", password = "userPass", roles = "USER")
  public void whenUserRequests_thenOwnerNameIsAbsent() throws Exception {
    mvc.perform(get("/items")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("Item 1"))
        .andExpect(jsonPath("$[0].ownerName").doesNotExist());
  }

  @Test
  @WithMockUser(username = "user", password = "userPass", roles = {"ADMIN", "USER"})
  public void whenMultipleRoles_thenExceptionIsThrown() throws Exception {
    try {
      mvc.perform(get("/items")).andExpect(status().isOk());
    } catch (NestedServletException e) {
      assertTrue(e.getCause() instanceof IllegalArgumentException);
      assertEquals("Ambiguous @JsonView declaration for roles ROLE_ADMIN,ROLE_USER",
          e.getCause().getMessage());
    }
  }
}
